package com.nikitasutulov.macsro.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.nikitasutulov.macsro.R
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
import androidx.core.content.edit

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
    private val usefulEventStatusesNames = listOf("Активна", "Завершена")

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
            FirebaseApp.getInstance().delete()
        }
    }

    private fun setupListeners() {
        setupLogoutButton()
        setupFilterButton()
        setupRefreshButton()
        setupSettingsButton()
        setupRatingButton()
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("Logout")
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    sessionManager.clearSession()
                    FirebaseApp.getInstance().delete()
                    navigateToLogin()
                }
                .setNegativeButton("No", null)
                .create()
                .show()
        }
    }

    private fun setupFilterButton() {
        binding.filterButton.setOnClickListener {
            val selectedType = binding.typeSpinner.selectedItem.toString()
            val selectedStatus = binding.statusSpinner.selectedItem.toString()
            val selectedDistrict = binding.districtSpinner.selectedItem.toString()

            val filteredEvents = events.filter { event ->
                (selectedType == "All" || event.eventType.name == selectedType) &&
                        (selectedStatus == "All" || event.eventStatus.name == selectedStatus) &&
                        (selectedDistrict == "All" || event.district.name == selectedDistrict)
            }

            eventAdapter.submitList(filteredEvents)
        }
    }

    private fun setupRefreshButton() {
        binding.refreshButton.setOnClickListener { fetchEvents() }
    }

    private fun setupSettingsButton() {
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_eventsFragment_to_settingsFragment)
        }
    }

    private fun setupRatingButton() {
        binding.ratingButton.setOnClickListener {
            val action = EventsFragmentDirections.actionEventsFragmentToVolunteerRatingFragment(user.id)
            findNavController().navigate(action)
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_eventsFragment_to_loginFragment)
    }

    private fun navigateToVolunteerCreation() {
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
                saveCurrentUserId(user.id)
                eventTypeViewModel.getAll("Bearer $token")
                eventTypeViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        eventTypes = response.data!!.associateBy { it.gid }
                        eventStatusViewModel.getAll("Bearer $token")
                    } else if (response is BaseResponse.Error) {
                        showFetchEventsError(response)
                    }
                }
                eventStatusViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        eventStatuses = response.data!!
                            .filter { it.name in usefulEventStatusesNames }
                            .associateBy { it.gid }
                        districtViewModel.getAll("Bearer $token")
                    } else if (response is BaseResponse.Error) {
                        showFetchEventsError(response)
                    }
                }
                districtViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        districts = response.data!!.associateBy { it.gid }
                        setupSpinners()
                        if (user.roles.any { it.name == "Coordinator" }) {
                            fetchEventsForCoordinator()
                        } else if (user.roles.any { it.name == "Volunteer" }) {
                            binding.settingsButton.visibility = View.VISIBLE
                            binding.ratingButton.visibility = View.VISIBLE
                            fetchEventsForVolunteer()
                        } else {
                            navigateToVolunteerCreation()
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

    private fun saveCurrentUserId(id: String) {
        requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE).edit {
            putString(
                "currentUserId",
                id
            )
        }
    }

    private fun setupSpinners() {
        val typeList = listOf("All") + eventTypes.values.map { it.name }
        val statusList = listOf("All") + eventStatuses.values.map { it.name }
        val districtList = listOf("All") + districts.values.map { it.name }

        binding.typeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeList)
        binding.statusSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, statusList)
        binding.districtSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districtList)
    }

    private fun fetchEventsForCoordinator() {
        var operationWorkerGID = ""
        operationWorkerViewModel.getByUserGID("Bearer $token", user.id)
        operationWorkerViewModel.getByUserGIDResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                operationWorkerGID = response.data!!.gid
                eventViewModel.getAll("Bearer $token")
            } else if (response is BaseResponse.Error) {
                showFetchEventsError(response)
            }
        }
        eventViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val eventsOfCoordinator = response.data!!
                    .filter { it.coordinatorGID == operationWorkerGID && eventStatuses[it.eventStatusGID]?.name in usefulEventStatusesNames }
                mapEvents(eventsOfCoordinator)
                eventAdapter.submitList(events)
            } else if (response is BaseResponse.Error) {
                showFetchEventsError(response)
            }
        }
    }

    private fun fetchEventsForVolunteer() {
        eventViewModel.getAll("Bearer $token")
        eventViewModel.getAllResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val eventsOfVolunteer= response.data!!
                    .filter { eventStatuses[it.eventStatusGID]?.name in usefulEventStatusesNames }
                mapEvents(eventsOfVolunteer)
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