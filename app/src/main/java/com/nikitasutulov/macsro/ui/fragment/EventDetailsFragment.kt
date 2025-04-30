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
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.ui.EventResource
import com.nikitasutulov.macsro.ui.adapter.EventResourceAdapter
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.operations.GroupViewModel
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
    private var volunteerGID: String = ""
    private lateinit var qrCodeScannerLauncher: ActivityResultLauncher<ScanOptions>
    private lateinit var eventResourcesAdapter: EventResourceAdapter

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
    }

    private fun setupRecyclerViews() {
        eventResourcesAdapter = EventResourceAdapter()
        binding.eventResourcesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventResourcesAdapter
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
                renderEventResources()
            } else if (validationResponse is BaseResponse.Error) {
                showUserCheckError(validationResponse)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}