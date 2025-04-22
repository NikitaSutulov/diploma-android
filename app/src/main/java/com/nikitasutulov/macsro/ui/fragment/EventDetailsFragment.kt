package com.nikitasutulov.macsro.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.nikitasutulov.macsro.data.ui.Event
import com.nikitasutulov.macsro.databinding.FragmentEventDetailsBinding
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.operations.GroupViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersGroupsViewModel

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: EventDetailsFragmentArgs by navArgs()
    private lateinit var event: Event
    private lateinit var sessionManager: SessionManager
    private lateinit var authViewModel: AuthViewModel
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var volunteersGroupsViewModel: VolunteersGroupsViewModel
    private lateinit var groupViewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

        initViewModels()
        sessionManager = SessionManager.getInstance(requireContext())
        renderEventDetails()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        volunteerViewModel = ViewModelProvider(this)[VolunteerViewModel::class.java]
        volunteersGroupsViewModel = ViewModelProvider(this)[VolunteersGroupsViewModel::class.java]
        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
    }

    private fun renderEventDetails() {
        event = args.event
        renderEventHeader()
        val token = sessionManager.getToken()
        var groupsOfVolunteer: Set<String> = setOf()
        var groupsInEvent: Set<String> = setOf()
        authViewModel.validateToken("Bearer $token")
        authViewModel.tokenValidationResponse.observe(viewLifecycleOwner) { validationResponse ->
            if (validationResponse is BaseResponse.Success) {
                Log.d("EventDetailsFragment", validationResponse.data.toString())
                val user = validationResponse.data!!.user!!
                val roles = user.roles
                if (roles.any { it.name == "Coordinator" }) {
                    renderCoordinatorScreen()
                } else if (roles.any { it.name == "Volunteer" }) {
                    volunteerViewModel.getByUserGID("Bearer $token", user.id)
                    volunteerViewModel.getByUserGIDResponse.observe(viewLifecycleOwner) { volunteerResponse ->
                        if (volunteerResponse is BaseResponse.Success) {
                            val volunteerGID = volunteerResponse.data!!.gid
                            volunteersGroupsViewModel.getByVolunteerGID("Bearer $token", volunteerGID)
                        }
                    }
                    volunteersGroupsViewModel.getByVolunteerGIDResponse.observe(viewLifecycleOwner) { volunteersGroupsResponse ->
                        if (volunteersGroupsResponse is BaseResponse.Success) {
                            groupsOfVolunteer = volunteersGroupsResponse.data!!.map { it.groupGID }.toSet()
                            groupViewModel.getByEventGID("Bearer $token", event.gid)
                        }
                    }
                    groupViewModel.getByEventGIDResponse.observe(viewLifecycleOwner) { groupsInEventResponse ->
                        if (groupsInEventResponse is BaseResponse.Success) {
                            groupsInEvent = groupsInEventResponse.data!!.map { it.gid }.toSet()
                            if (groupsOfVolunteer.toSet().intersect(groupsInEvent.toSet()).isNotEmpty()) {
                                renderVolunteerScreen()
                            } else {
                                renderNotJoinedScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun renderCoordinatorScreen() {
        binding.eventCoordinatorView.visibility = View.VISIBLE
    }

    private fun renderVolunteerScreen() {
        binding.eventVolunteerView.visibility = View.VISIBLE
    }

    private fun renderNotJoinedScreen() {
        binding.eventLockJoinButton.visibility = View.VISIBLE
        binding.eventLockJoinButton.setOnClickListener {
            Toast.makeText(
                requireActivity(),
                "Clicked on link to join the event",
                Toast.LENGTH_SHORT
            ).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}