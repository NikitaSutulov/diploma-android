package com.nikitasutulov.macsro.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.navArgs
import com.nikitasutulov.macsro.data.ui.Event
import com.nikitasutulov.macsro.databinding.FragmentEventDetailsBinding
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.ui.EventResource
import com.nikitasutulov.macsro.data.ui.GroupMember
import com.nikitasutulov.macsro.data.ui.OperationTask
import com.nikitasutulov.macsro.ui.adapter.EventResourceAdapter
import com.nikitasutulov.macsro.ui.adapter.GroupMemberAdapter
import com.nikitasutulov.macsro.ui.adapter.OperationTaskAdapter
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.operations.GroupViewModel
import com.nikitasutulov.macsro.viewmodel.operations.OperationTaskStatusViewModel
import com.nikitasutulov.macsro.viewmodel.operations.OperationTaskViewModel
import com.nikitasutulov.macsro.viewmodel.operations.ResourcesEventViewModel
import com.nikitasutulov.macsro.viewmodel.utils.ResourceViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersEventsViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersGroupsViewModel
import org.json.JSONObject

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: EventDetailsFragmentArgs by navArgs()
    private lateinit var event: Event
    private lateinit var sessionManager: SessionManager
    private lateinit var authViewModel: AuthViewModel
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var volunteersGroupsViewModel: VolunteersGroupsViewModel
    private lateinit var volunteersEventsViewModel: VolunteersEventsViewModel
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var resourcesEventViewModel: ResourcesEventViewModel
    private lateinit var resourceViewModel: ResourceViewModel
    private lateinit var operationTaskViewModel: OperationTaskViewModel
    private lateinit var operationTaskStatusViewModel: OperationTaskStatusViewModel
    private var volunteerGID: String = ""
    private lateinit var qrCodeScannerLauncher: ActivityResultLauncher<ScanOptions>
    private lateinit var eventResourcesAdapter: EventResourceAdapter
    private lateinit var groupMembersAdapter: GroupMemberAdapter
    private lateinit var operationTasksAdapter: OperationTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrCodeScannerLauncher = registerForActivityResult(ScanContract()) {result ->
            if (result.contents != null) {
                val codeData = JSONObject(result.contents)
                try {
                    val codeVolunteerGID = codeData.getString("volunteerGID")
                    val codeEventGID = codeData.getString("eventGID")
                    addVolunteerToEvent(codeVolunteerGID, codeEventGID)
                } catch (e: Exception) {
                    Snackbar.make(
                        binding.root,
                        "Invalid QR code",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

        initViewModels()
        sessionManager = SessionManager.getInstance(requireContext())
        setupRecyclerViews()
        renderEventDetails()
        setupRefreshButton()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        volunteerViewModel = ViewModelProvider(this)[VolunteerViewModel::class.java]
        volunteersGroupsViewModel = ViewModelProvider(this)[VolunteersGroupsViewModel::class.java]
        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        volunteersEventsViewModel = ViewModelProvider(this)[VolunteersEventsViewModel::class.java]
        resourcesEventViewModel = ViewModelProvider(this)[ResourcesEventViewModel::class.java]
        resourceViewModel = ViewModelProvider(this)[ResourceViewModel::class.java]
        operationTaskViewModel = ViewModelProvider(this)[OperationTaskViewModel::class.java]
        operationTaskStatusViewModel = ViewModelProvider(this)[OperationTaskStatusViewModel::class.java]
    }

    private fun setupRecyclerViews() {
        eventResourcesAdapter = EventResourceAdapter()
        binding.eventResourcesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventResourcesAdapter
        }
        groupMembersAdapter = GroupMemberAdapter()
        binding.volunteerGroupRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupMembersAdapter
        }
        operationTasksAdapter = OperationTaskAdapter()
        binding.groupOperationTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = operationTasksAdapter
        }
    }

    private fun renderEventDetails() {
        event = args.event
        renderEventHeader()
        val token = sessionManager.getToken()
        authViewModel.validateToken("Bearer $token")
        authViewModel.tokenValidationResponse.observeOnce(viewLifecycleOwner) { validationResponse ->
            if (validationResponse is BaseResponse.Success) {
                val user = validationResponse.data!!.user!!
                val roles = user.roles
                if (roles.any { it.name == "Coordinator" }) {
                    renderCoordinatorScreen()
                } else if (roles.any { it.name == "Volunteer" }) {
                    volunteerViewModel.getByUserGID("Bearer $token", user.id)
                }
                renderEventResources()
            } else if (validationResponse is BaseResponse.Error) {
                showUserCheckError(validationResponse)
            }
        }
        volunteerViewModel.getByUserGIDResponse.observeOnce(viewLifecycleOwner) { volunteerResponse ->
            if (volunteerResponse is BaseResponse.Success) {
                volunteerGID = volunteerResponse.data!!.gid
                volunteersEventsViewModel.getByVolunteerGID("Bearer $token", volunteerGID)
            } else if (volunteerResponse is BaseResponse.Error) {
                showUserCheckError(volunteerResponse)
            }
        }
        volunteersEventsViewModel.getByVolunteerGIDResponse.observeOnce(viewLifecycleOwner) { eventsOfVolunteerResponse ->
            if (eventsOfVolunteerResponse is BaseResponse.Success) {
                val eventsOfVolunteer = eventsOfVolunteerResponse.data!!
                if (eventsOfVolunteer.any { it.eventGID == event.gid }) {
                    renderVolunteerScreen()
                } else {
                    renderNotJoinedScreen()
                }
            } else if (eventsOfVolunteerResponse is BaseResponse.Error) {
                showUserCheckError(eventsOfVolunteerResponse)
            }
        }
    }

    private fun renderEventResources() {
        val token = sessionManager.getToken()
        var resourcesEventDtos: List<ResourcesEventDto> = listOf()
        var resourceDtos: List<ResourceDto>
        val eventResources: MutableList<EventResource> = mutableListOf()
        resourcesEventViewModel.getByEventGID("Bearer $token", event.gid)
        resourcesEventViewModel.getByEventGIDResponse.observeOnce(viewLifecycleOwner) { resourcesEventsResponse ->
            if (resourcesEventsResponse is BaseResponse.Success) {
                resourcesEventDtos = resourcesEventsResponse.data!!
                resourceViewModel.getAll("Bearer $token", null, null)
            } else if (resourcesEventsResponse is BaseResponse.Error) {
                showEventResourcesError(resourcesEventsResponse)
            }
        }
        resourceViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { resourcesResponse ->
            if (resourcesResponse is BaseResponse.Success) {
                resourceDtos = resourcesResponse.data!!.filter {
                        resourceDto -> resourcesEventDtos.any {
                            it.resourceGID == resourceDto.gid
                        }
                }
                for (resourcesEventDto in resourcesEventDtos) {
                    eventResources.add(
                        EventResource(
                            gid = resourcesEventDto.gid,
                            resourceName = resourceDtos.find { it.gid == resourcesEventDto.resourceGID }!!.name,
                            requiredQuantity = resourcesEventDto.requiredQuantity,
                            availableQuantity = resourcesEventDto.availableQuantity
                        )
                    )
                }
                eventResourcesAdapter.submitList(eventResources)
            } else if (resourcesResponse is BaseResponse.Error) {
                showEventResourcesError(resourcesResponse)
            }
        }
    }

    private fun renderEventHeader() {
        binding.eventNameTextView.text = event.name
        binding.eventTypeTextView.text = event.eventType.name
        binding.eventStatusTextView.text = event.eventStatus.name
        binding.eventDistrictTextView.text = event.district.name
        binding.eventLocationLink.setOnClickListener {
            val uri =
                "geo:${event.latitude},${event.longitude}?q=${event.latitude},${event.longitude}(${
                    Uri.encode(event.name)
                })".toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
    }

    private fun renderCoordinatorScreen() {
        binding.eventCoordinatorView.visibility = View.VISIBLE
        binding.addVolunteerToEventButton.setOnClickListener {
            qrCodeScannerLauncher.launch(ScanOptions().apply {
                setOrientationLocked(true)
            })
        }
    }

    private fun renderVolunteerScreen() {
        binding.eventVolunteerView.visibility = View.VISIBLE
        renderVolunteerGroupMembers()
    }

    private fun renderVolunteerGroupMembers() {
        val token = sessionManager.getToken()
        var volunteersGroups: List<VolunteersGroupsDto> = listOf()
        var eventGroups: List<GroupDto> = listOf()
        var volunteerGroupDto: GroupDto? = null
        var isVolunteerLeader: Boolean
        groupViewModel.getByEventGID("Bearer $token", event.gid)
        groupViewModel.getByEventGIDResponse.observeOnce(viewLifecycleOwner) { eventGroupResponse ->
            if (eventGroupResponse is BaseResponse.Success) {
                eventGroups = eventGroupResponse.data!!
                volunteersGroupsViewModel.getByVolunteerGID("Bearer $token", volunteerGID)
            } else if (eventGroupResponse is BaseResponse.Error) {
                showGroupMembersError(eventGroupResponse)
            }
        }
        volunteersGroupsViewModel.getByVolunteerGIDResponse.observeOnce(viewLifecycleOwner) { volunteersGroupsResponse ->
            if (volunteersGroupsResponse is BaseResponse.Success) {
                volunteersGroups = volunteersGroupsResponse.data!!
                val eventGroupGIDS = eventGroups.map { it.gid }.toSet()
                val volunteerGroupGIDs = volunteersGroups.map { it.groupGID }.toSet()
                val intersection = eventGroupGIDS.intersect(volunteerGroupGIDs).toList()
                if (intersection.isNotEmpty()) {
                    binding.noGroupLayout.visibility = View.GONE
                    val volunteerGroupGID = intersection[0]
                    volunteerGroupDto = eventGroups.find { it.gid == volunteerGroupGID }
                    renderGroupOperationTasks(volunteerGroupDto!!)
                    isVolunteerLeader = volunteerGroupDto!!.leaderGID == volunteerGID
                    binding.groupChatButton.visibility = View.VISIBLE
                    binding.coordinationChatButton.visibility = View.GONE
                    if (isVolunteerLeader) {
                        binding.coordinationChatButton.visibility = View.VISIBLE
                    }
                    val groupNameText = getString(R.string.your_group) + volunteerGroupDto!!.name
                    binding.volunteerGroupNameTextView.text = groupNameText
                    volunteerViewModel.getByGroupGID("Bearer $token", volunteerGroupGID)
                } else {
                    renderNoGroupLayout()
                }
            } else if (volunteersGroupsResponse is BaseResponse.Error) {
                showGroupMembersError(volunteersGroupsResponse)
            }
        }
        volunteerViewModel.getByGroupGIDResponse.observeOnce(viewLifecycleOwner) { volunteersInGroupResponse ->
            if (volunteersInGroupResponse is BaseResponse.Success) {
                val volunteersInGroup = volunteersInGroupResponse.data!!
                val groupMembers = volunteersInGroup.map {
                    GroupMember(
                        gid = it.gid,
                        name = "${it.name} ${it.surname}",
                        isLeader = it.gid == volunteerGroupDto!!.leaderGID
                    )
                }
                groupMembersAdapter.submitList(groupMembers)
            } else if (volunteersInGroupResponse is BaseResponse.Error) {
                showGroupMembersError(volunteersInGroupResponse)
            }
        }
    }

    private fun renderGroupOperationTasks(groupDto: GroupDto) {
        val token = sessionManager.getToken()
        var operationTaskStatuses: List<OperationTaskStatusDto> = listOf()
        operationTaskStatusViewModel.getAll("Bearer $token", null, null)
        operationTaskStatusViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { operationTaskStatusesResponse ->
            if (operationTaskStatusesResponse is BaseResponse.Success) {
                operationTaskStatuses = operationTaskStatusesResponse.data!!
                operationTaskViewModel.getByGroupGID("Bearer $token", groupDto.gid)
            } else if (operationTaskStatusesResponse is BaseResponse.Error) {
                showOperationTasksError(operationTaskStatusesResponse)
            }
        }
        operationTaskViewModel.getByGroupGIDResponse.observeOnce(viewLifecycleOwner) { operationTasksResponse ->
            if (operationTasksResponse is BaseResponse.Success) {
                val operationTaskDtos = operationTasksResponse.data!!
                val operationTasks = operationTaskDtos.map {
                    OperationTask(
                        gid = it.gid,
                        name = it.name,
                        taskDescription = it.taskDescription,
                        groupName = groupDto.name,
                        taskStatusName = operationTaskStatuses.find { status -> status.gid == it.taskStatusGID }!!.name
                    )
                }
                operationTasksAdapter.submitList(operationTasks)
            } else if (operationTasksResponse is BaseResponse.Error) {
                showOperationTasksError(operationTasksResponse)
            }
        }
    }

    private fun renderNoGroupLayout() {
        binding.noGroupLayout.visibility = View.VISIBLE
        binding.volunteerGroupNameTextView.text = getString(R.string.your_group)
    }

    private fun renderNotJoinedScreen() {
        binding.eventLockJoinButton.visibility = View.VISIBLE
        binding.eventLockJoinButton.setOnClickListener {
            val action =
                EventDetailsFragmentDirections.actionEventDetailsFragmentToJoinEventFragment(
                    volunteerGID,
                    event
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRefreshButton() {
        binding.noGroupRefreshButton.setOnClickListener {
            renderEventDetails()
        }
    }

    private fun addVolunteerToEvent(volunteerGID: String, eventGID: String) {
        val token = sessionManager.getToken()
        volunteersEventsViewModel.create(
            "Bearer $token",
            CreateVolunteersEventsDto(volunteerGID, eventGID)
        )
        volunteersEventsViewModel.createResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                Snackbar.make(
                    binding.root,
                    "Volunteer has been added to the event successfully!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showUserCheckError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to check the user. ${response.error?.message}")
    }

    private fun showEventResourcesError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get event resources. ${response.error?.message}")
    }

    private fun showGroupMembersError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get group members. ${response.error?.message}")
    }

    private fun showOperationTasksError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get operation tasks. ${response.error?.message}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
