package com.nikitasutulov.macsro.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.ui.Event
import com.nikitasutulov.macsro.databinding.FragmentEventsBinding
import com.nikitasutulov.macsro.ui.adapter.EventAdapter
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.operations.EventStatusViewModel
import com.nikitasutulov.macsro.viewmodel.operations.EventTypeViewModel
import com.nikitasutulov.macsro.viewmodel.operations.EventViewModel
import com.nikitasutulov.macsro.viewmodel.operations.OperationWorkerViewModel
import com.nikitasutulov.macsro.viewmodel.utils.DistrictViewModel

class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var eventAdapter: EventAdapter
    private lateinit var authViewModel: AuthViewModel
    private lateinit var eventViewModel: EventViewModel
    private lateinit var eventTypeViewModel: EventTypeViewModel
    private lateinit var eventStatusViewModel: EventStatusViewModel
    private lateinit var districtViewModel: DistrictViewModel
    private lateinit var operationWorkerViewModel: OperationWorkerViewModel
    private lateinit var events: List<Event>
    private lateinit var eventTypes: Map<String, EventTypeDto>
    private lateinit var eventStatuses: Map<String, EventStatusDto>
    private lateinit var districts: Map<String, DistrictDto>
    private lateinit var user: UserDto
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        sessionManager = SessionManager.getInstance(requireContext())
        checkAuthState()
        setupListeners()
        initViewModels()
        fetchEvents()
        setupRecyclerView()

        return binding.root
    }

    private fun checkAuthState() {
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun setupListeners() {
        setupLogoutButton()
        setupRefreshButton()
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("Logout")
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    sessionManager.clearSession()
                    navigateToLogin()
                }
                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()
        }
    }

    private fun setupRefreshButton() {
        binding.refreshButton.setOnClickListener { fetchEvents() }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.loginFragment)
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]
        eventTypeViewModel = ViewModelProvider(this)[EventTypeViewModel::class.java]
        eventStatusViewModel = ViewModelProvider(this)[EventStatusViewModel::class.java]
        districtViewModel = ViewModelProvider(this)[DistrictViewModel::class.java]
        operationWorkerViewModel = ViewModelProvider(this)[OperationWorkerViewModel::class.java]
    }

    private fun fetchEvents() {
        token = sessionManager.getToken()

        authViewModel.validateToken("Bearer $token")
        authViewModel.tokenValidationResponse.observeOnce(viewLifecycleOwner) { validationResponse ->
            if (validationResponse is BaseResponse.Success) {
                user = validationResponse.data!!.user!!
                eventTypeViewModel.getAll("Bearer $token", null, null)
                eventTypeViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        eventTypes = response.data!!.associateBy { it.gid }
                        eventStatusViewModel.getAll("Bearer $token", null, null)
                    } else if (response is BaseResponse.Error) {
                        showFetchEventsError(response)
                    }
                }
                eventStatusViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        eventStatuses = response.data!!.associateBy { it.gid }
                        districtViewModel.getAll("Bearer $token", null, null)
                    } else if (response is BaseResponse.Error) {
                        showFetchEventsError(response)
                    }
                }
                districtViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        districts = response.data!!.associateBy { it.gid }
                        if (user.roles.any { it.name == "Coordinator" }) {
                            fetchEventsForCoordinator()
                        } else if (user.roles.any { it.name == "Volunteer" }) {
                            fetchEventsForVolunteer()
                        }
                    } else if (response is BaseResponse.Error) {
                        showFetchEventsError(response)
                    }
                }
            } else if (validationResponse is BaseResponse.Error) {
                showFetchEventsError(validationResponse)
            }
        }
    }

    private fun fetchEventsForCoordinator() {
        var operationWorkerGID = ""
        operationWorkerViewModel.getByUserGID("Bearer $token", user.id)
        operationWorkerViewModel.getByUserGIDResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                operationWorkerGID = response.data!!.gid
                eventViewModel.getAll("Bearer $token", null, null)
            } else if (response is BaseResponse.Error) {
                showFetchEventsError(response)
            }
        }
        eventViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val eventsOfCoordinator = response.data!!.filter { it.coordinatorGID == operationWorkerGID }
                mapEvents(eventsOfCoordinator)
                eventAdapter.submitList(events)
            } else if (response is BaseResponse.Error) {
                showFetchEventsError(response)
            }
        }
    }

    private fun fetchEventsForVolunteer() {
        eventViewModel.getAll("Bearer $token", null, null)
        eventViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                mapEvents(response.data!!)
                eventAdapter.submitList(events)
            } else if (response is BaseResponse.Error) {
                showFetchEventsError(response)
            }
        }
    }

    private fun showFetchEventsError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get Events. ${response.error?.message}")
    }

    private fun mapEvents(eventDtos: List<EventDto>) {
        events = eventDtos.map { dto ->
            Event(
                gid = dto.gid,
                name = dto.name,
                longitude = dto.longitude,
                latitude = dto.latitude,
                eventType = eventTypes[dto.eventTypeGID]!!,
                eventStatus = eventStatuses[dto.eventStatusGID]!!,
                district = districts[dto.districtGID]!!,
                coordinatorGID = dto.coordinatorGID,
                dispatcherGID = dto.dispatcherGID
            )
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter { event ->
            val action = EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment(event)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}