package com.nikitasutulov.macsro.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.UpdateRatingDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.ui.Event
import com.nikitasutulov.macsro.data.ui.EventResource
import com.nikitasutulov.macsro.data.ui.Group
import com.nikitasutulov.macsro.data.ui.GroupMember
import com.nikitasutulov.macsro.data.ui.OperationTask
import com.nikitasutulov.macsro.databinding.FragmentEventDetailsBinding
import com.nikitasutulov.macsro.ui.adapter.EventResourceAdapter
import com.nikitasutulov.macsro.ui.adapter.GroupAdapter
import com.nikitasutulov.macsro.ui.adapter.OperationTaskAdapter
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.operations.GroupViewModel
import com.nikitasutulov.macsro.viewmodel.operations.OperationTaskStatusViewModel
import com.nikitasutulov.macsro.viewmodel.operations.OperationTaskViewModel
import com.nikitasutulov.macsro.viewmodel.operations.ResourcesEventViewModel
import com.nikitasutulov.macsro.viewmodel.utils.MeasurementUnitViewModel
import com.nikitasutulov.macsro.viewmodel.utils.ResourceViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersEventsViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersGroupsViewModel
import org.json.JSONObject

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: EventDetailsFragmentArgs by navArgs()
    private lateinit var user: UserDto
    private lateinit var event: Event
    private lateinit var sessionManager: SessionManager
    private lateinit var authViewModel: AuthViewModel
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var volunteersGroupsViewModel: VolunteersGroupsViewModel
    private lateinit var volunteersEventsViewModel: VolunteersEventsViewModel
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var resourcesEventViewModel: ResourcesEventViewModel
    private lateinit var resourceViewModel: ResourceViewModel
    private lateinit var measurementUnitViewModel: MeasurementUnitViewModel
    private lateinit var operationTaskViewModel: OperationTaskViewModel
    private lateinit var operationTaskStatusViewModel: OperationTaskStatusViewModel
    private var volunteerGID: String = ""
    private lateinit var qrCodeScannerLauncher: ActivityResultLauncher<ScanOptions>
    private lateinit var eventResourcesAdapter: EventResourceAdapter
    private lateinit var coordinatorGroupAdapter: GroupAdapter
    private lateinit var volunteerGroupAdapter: GroupAdapter
    private lateinit var groupOperationTasksAdapter: OperationTaskAdapter
    private lateinit var eventOperationTasksAdapter: OperationTaskAdapter

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
                        getString(R.string.invalid_qr_code),
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
        measurementUnitViewModel = ViewModelProvider(this)[MeasurementUnitViewModel::class.java]
        operationTaskViewModel = ViewModelProvider(this)[OperationTaskViewModel::class.java]
        operationTaskStatusViewModel = ViewModelProvider(this)[OperationTaskStatusViewModel::class.java]
    }

    private fun setupRecyclerViews() {
        eventResourcesAdapter = EventResourceAdapter()
        binding.eventResourcesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventResourcesAdapter
        }
        coordinatorGroupAdapter = GroupAdapter().apply {
            setOnMemberClickListener { member ->
                showMemberRatingUpdateDialog(member)
            }
        }
        binding.coordinatorGroupRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coordinatorGroupAdapter
        }
        volunteerGroupAdapter = GroupAdapter()
        binding.volunteerGroupRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = volunteerGroupAdapter
        }
        groupOperationTasksAdapter = OperationTaskAdapter()
        binding.groupOperationTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupOperationTasksAdapter
        }
        eventOperationTasksAdapter = OperationTaskAdapter()
        binding.eventOperationTasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventOperationTasksAdapter
        }
    }

    private fun showMemberRatingUpdateDialog(member: GroupMember) {
        val values = arrayOf("-1", "0", "1")
        var selectedIndex = 1
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.adjust_rating_points_for, member.name))
            .setSingleChoiceItems(values, selectedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton("OK") { _, _ ->
                val token = sessionManager.getToken()
                val selectedValue = values[selectedIndex].toInt()
                val ratingUpdateLiveData = volunteerViewModel.updateRating(
                    "Bearer $token",
                    UpdateRatingDto(member.gid, selectedValue)
                )
                ratingUpdateLiveData.observeOnce(viewLifecycleOwner) { ratingUpdateResponse ->
                    if (ratingUpdateResponse is BaseResponse.Success) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.successfully_updated_rating_for, member.name),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else if (ratingUpdateResponse is BaseResponse.Error) {
                        showRatingUpdateError(ratingUpdateResponse)
                    }
                }

            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun renderEventDetails() {
        event = args.event
        renderEventHeader()
        val token = sessionManager.getToken()
        authViewModel.validateToken("Bearer $token")
        authViewModel.tokenValidationResponse.observeOnce(viewLifecycleOwner) { validationResponse ->
            if (validationResponse is BaseResponse.Success) {
                user = validationResponse.data!!.user!!
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
        var measurementUnitDtos: List<MeasurementUnitDto> = listOf()
        val eventResources: MutableList<EventResource> = mutableListOf()
        resourcesEventViewModel.getByEventGID("Bearer $token", event.gid)
        resourcesEventViewModel.getByEventGIDResponse.observeOnce(viewLifecycleOwner) { resourcesEventsResponse ->
            if (resourcesEventsResponse is BaseResponse.Success) {
                resourcesEventDtos = resourcesEventsResponse.data!!
                measurementUnitViewModel.getAll("Bearer $token")
            } else if (resourcesEventsResponse is BaseResponse.Error) {
                showEventResourcesError(resourcesEventsResponse)
            }
        }
        measurementUnitViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { measurementUnitsResponse ->
            if (measurementUnitsResponse is BaseResponse.Success) {
                measurementUnitDtos = measurementUnitsResponse.data!!
                resourceViewModel.getAll("Bearer $token")
            } else if (measurementUnitsResponse is BaseResponse.Error) {
                showEventResourcesError(measurementUnitsResponse)
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
                            measurementUnitName = measurementUnitDtos.find { it.gid == resourcesEventDto.measurementUnitGID }!!.name,
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
        binding.requestsButton.setOnClickListener {
            val action = EventDetailsFragmentDirections.actionEventDetailsFragmentToRequestsFragment(event)
            findNavController().navigate(action)
        }
        val roomId = "room_${event.gid}_${event.coordinatorGID}"
        val roomName = "${event.name} Coordination"
        setupCoordinationChat(roomId, roomName, binding.coordinatorCoordinationChatButton)
        renderEventGroupsAndTasks()
    }

    private fun renderEventGroupsAndTasks() {
        val token = sessionManager.getToken()
        var eventGroupDtos: List<GroupDto> = listOf()
        val groupsMembersMap: MutableMap<String, List<VolunteerDto>> = mutableMapOf()
        groupViewModel.getByEventGID("Bearer $token", event.gid)
        groupViewModel.getByEventGIDResponse.observeOnce(viewLifecycleOwner) { eventGroupResponse ->
            if (eventGroupResponse is BaseResponse.Success) {
                eventGroupDtos = eventGroupResponse.data!!
                for (groupDto in eventGroupDtos) {
                    val groupVolunteersLiveData = volunteerViewModel.getByGroupGID("Bearer $token", groupDto.gid)
                    groupVolunteersLiveData.observeOnce(viewLifecycleOwner) { groupVolunteersResponse ->
                        if (groupVolunteersResponse is BaseResponse.Success) {
                            val volunteerDtosInGroup = groupVolunteersResponse.data!!
                            groupsMembersMap[groupDto.gid] = volunteerDtosInGroup
                            if (groupsMembersMap.keys.size == eventGroupDtos.size) {
                                val groups = eventGroupDtos.map {
                                    val groupMembers = groupsMembersMap[it.gid]!!.map { volunteerDto ->
                                        GroupMember(
                                            gid = volunteerDto.gid,
                                            name = "${volunteerDto.name} ${volunteerDto.surname}",
                                            isLeader = volunteerDto.gid == it.leaderGID
                                        )
                                    }
                                    Group(
                                        gid = it.gid,
                                        name = it.name,
                                        members = groupMembers
                                    )
                                }
                                coordinatorGroupAdapter.submitList(groups)
                                renderEventOperationTasks(eventGroupDtos)
                            }
                        } else if (groupVolunteersResponse is BaseResponse.Error) {
                            showGroupMembersError(groupVolunteersResponse)
                        }

                    }
                }
            } else if (eventGroupResponse is BaseResponse.Error) {
                showGroupMembersError(eventGroupResponse)
            }
        }
    }

    private fun renderEventOperationTasks(groupDtos: List<GroupDto>) {
        val token = sessionManager.getToken()
        var operationTaskStatuses: List<OperationTaskStatusDto> = listOf()
        operationTaskStatusViewModel.getAll("Bearer $token")
        operationTaskStatusViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { operationTaskStatusesResponse ->
            if (operationTaskStatusesResponse is BaseResponse.Success) {
                operationTaskStatuses = operationTaskStatusesResponse.data!!
                for (groupDto in groupDtos) {
                    val operationTasksLiveData = operationTaskViewModel.getByGroupGID("Bearer $token", groupDto.gid)
                    operationTasksLiveData.observeOnce(viewLifecycleOwner) { operationTasksResponse ->
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
                            eventOperationTasksAdapter.submitList(operationTasks)
                            binding.eventOperationTasksLayout.visibility = View.VISIBLE
                        } else if (operationTasksResponse is BaseResponse.Error) {
                            showOperationTasksError(operationTasksResponse)
                        }
                    }
                }
            } else if (operationTaskStatusesResponse is BaseResponse.Error) {
                showOperationTasksError(operationTaskStatusesResponse)
            }
        }
    }

    private fun renderVolunteerScreen() {
        binding.eventVolunteerView.visibility = View.VISIBLE
        renderVolunteerGroupMembersAndTasks()
    }

    private fun renderVolunteerGroupMembersAndTasks() {
        val token = sessionManager.getToken()
        var volunteersGroups: List<VolunteersGroupsDto>
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
                    var roomId = "room_${event.gid}_${volunteerGroupGID}"
                    var roomName = "${event.name} ${volunteerGroupDto!!.name}"
                    setupGroupChat(roomId, roomName)
                    binding.groupLeaderCoordinationChatButton.visibility = View.GONE
                    if (isVolunteerLeader) {
                        binding.groupLeaderCoordinationChatButton.visibility = View.VISIBLE
                        roomId = "room_${event.gid}_${event.coordinatorGID}"
                        roomName = "${event.name} ${getString(R.string.coordination)}"
                        setupCoordinationChat(roomId, roomName, binding.groupLeaderCoordinationChatButton)
                    }
                    val groupVolunteersLiveData = volunteerViewModel.getByGroupGID("Bearer $token", volunteerGroupGID)
                    groupVolunteersLiveData.observeOnce(viewLifecycleOwner) { volunteersInGroupResponse ->
                        if (volunteersInGroupResponse is BaseResponse.Success) {
                            val volunteersInGroup = volunteersInGroupResponse.data!!
                            val groupMembers = volunteersInGroup.map {
                                GroupMember(
                                    gid = it.gid,
                                    name = "${it.name} ${it.surname}",
                                    isLeader = it.gid == volunteerGroupDto!!.leaderGID
                                )
                            }
                            val volunteerGroup = Group(
                                gid = volunteerGroupDto!!.gid,
                                name = volunteerGroupDto!!.name,
                                members = groupMembers
                            )
                            volunteerGroupAdapter.submitList(listOf(volunteerGroup))
                        } else if (volunteersInGroupResponse is BaseResponse.Error) {
                            showGroupMembersError(volunteersInGroupResponse)
                        }
                    }
                } else {
                    renderNoGroupLayout()
                }
            } else if (volunteersGroupsResponse is BaseResponse.Error) {
                showGroupMembersError(volunteersGroupsResponse)
            }
        }
    }

    private fun renderGroupOperationTasks(groupDto: GroupDto) {
        val token = sessionManager.getToken()
        var operationTaskStatuses: List<OperationTaskStatusDto> = listOf()
        operationTaskStatusViewModel.getAll("Bearer $token")
        operationTaskStatusViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { operationTaskStatusesResponse ->
            if (operationTaskStatusesResponse is BaseResponse.Success) {
                operationTaskStatuses = operationTaskStatusesResponse.data!!
                val operationTasksLiveData = operationTaskViewModel.getByGroupGID("Bearer $token", groupDto.gid)
                operationTasksLiveData.observeOnce(viewLifecycleOwner) { operationTasksResponse ->
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
                        groupOperationTasksAdapter.submitList(operationTasks)
                        binding.groupOperationTasksLayout.visibility = View.VISIBLE
                    } else if (operationTasksResponse is BaseResponse.Error) {
                        showOperationTasksError(operationTasksResponse)
                    }
                }
            } else if (operationTaskStatusesResponse is BaseResponse.Error) {
                showOperationTasksError(operationTaskStatusesResponse)
            }
        }
    }

    private fun setupGroupChat(roomId: String, roomName: String) {
        createChatRoomIfNotExists(
            roomId,
            roomName,
            {
                FirebaseMessaging.getInstance().subscribeToTopic(roomId)
                binding.groupChatButton.setOnClickListener {
                    val action = EventDetailsFragmentDirections.actionEventDetailsFragmentToChatFragment(roomId, user.id, roomName, user.name)
                    findNavController().navigate(action)
                }
            },
            { errorMessage ->
                handleError(binding.root, errorMessage)
            }
        )
    }

    private fun setupCoordinationChat(roomId: String, roomName: String, button: Button) {
        createChatRoomIfNotExists(
            roomId,
            roomName,
            {
                FirebaseMessaging.getInstance().subscribeToTopic(roomId)
                button.setOnClickListener {
                    val action = EventDetailsFragmentDirections.actionEventDetailsFragmentToChatFragment(roomId, user.id, roomName, user.name)
                    findNavController().navigate(action)
                }
            },
            { errorMessage ->
                handleError(binding.root, errorMessage)
            }
        )
    }

    private fun createChatRoomIfNotExists(
        id: String,
        name: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("chatRooms")
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    val newRoom = hashMapOf(
                        "name" to name,
                        "createdAt" to FieldValue.serverTimestamp(),
                    )
                    db.collection("chatRooms").document(id).set(newRoom)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e.message ?: "Unknown error") }
                } else {
                    onSuccess()
                }
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Unknown error") }
    }

    private fun renderNoGroupLayout() {
        binding.noGroupLayout.visibility = View.VISIBLE
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
                    getString(R.string.volunteer_has_been_added_to_the_event_successfully),
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

    private fun showRatingUpdateError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to update rating. ${response.error?.message}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
