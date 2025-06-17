package com.nikitasutulov.macsro.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.databinding.FragmentSettingsBinding
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.utils.DistrictViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersDistrictsViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var districtViewModel: DistrictViewModel
    private lateinit var volunteersDistrictsViewModel: VolunteersDistrictsViewModel
    private lateinit var sessionManager: SessionManager
    private var token: String? = null
    private var volunteer: VolunteerDto? = null
    private var districts: List<DistrictDto> = listOf()
    private var volunteersDistricts: List<VolunteersDistrictsDto> = listOf()
    private var previouslySelectedDistrictGIDs: Set<String> = setOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        loadNotificationSettings()
        setupDistrictsList()
        setupSaveButton()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        volunteerViewModel = ViewModelProvider(this)[VolunteerViewModel::class.java]
        districtViewModel = ViewModelProvider(this)[DistrictViewModel::class.java]
        volunteersDistrictsViewModel =
            ViewModelProvider(this)[VolunteersDistrictsViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun loadNotificationSettings() {
        val isSendGeoNotifications = sessionManager.getIsSendGeoNotifications()
        binding.nearbyEventsCheckbox.isChecked = isSendGeoNotifications
        binding.distanceLayout.visibility = if (isSendGeoNotifications) View.VISIBLE else View.INVISIBLE
        binding.nearbyEventsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            binding.distanceLayout.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }
        if (isSendGeoNotifications) {
            val maxDistance = sessionManager.getMaxDistance()
            binding.maxDistanceEditText.setText(maxDistance.toString())
        }
    }

    private fun setupDistrictsList() {
        token = sessionManager.getToken()
        authViewModel.validateToken("Bearer $token")
        authViewModel.tokenValidationResponse.observeOnce(viewLifecycleOwner) { validationResponse ->
            if (validationResponse is BaseResponse.Success) {
                val user = validationResponse.data!!.user!!
                binding.usernameTextView.text = user.name
                binding.emailTextView.text = user.email
                volunteerViewModel.getByUserGID("Bearer $token", user.id)
                volunteerViewModel.getByUserGIDResponse.observeOnce(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        volunteer = response.data!!
                        val volunteerFullName =
                            "${volunteer?.surname} ${volunteer?.name} ${volunteer?.secondName}"
                        binding.volunteerNameTextView.text = volunteerFullName
                        districtViewModel.getAll("Bearer $token")
                    } else if (response is BaseResponse.Error) {
                        showSettingsError(response)
                    }
                }
                districtViewModel.getAllResponse.observe(viewLifecycleOwner) { response ->
                    if (response is BaseResponse.Success) {
                        districts = response.data!!.sortedBy { it.name }
                        val districtsNames = districts.map { it.name }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_list_item_multiple_choice,
                            districtsNames
                        )
                        binding.districtsListView.adapter = adapter
                        volunteersDistrictsViewModel.getByVolunteerGID(
                            "Bearer $token",
                            volunteer?.gid!!
                        )
                    } else if (response is BaseResponse.Error) {
                        showSettingsError(response)
                    }
                }
                volunteersDistrictsViewModel.getByVolunteerGIDResponse.observeOnce(
                    viewLifecycleOwner
                ) { response ->
                    if (response is BaseResponse.Success) {
                        volunteersDistricts = response.data!!
                        previouslySelectedDistrictGIDs =
                            volunteersDistricts.map { it.districtGID }.toSet()
                        for (i in districts.indices) {
                            val district = districts[i]
                            if (previouslySelectedDistrictGIDs.contains(district.gid)) {
                                binding.districtsListView.setItemChecked(i, true)
                            }
                        }
                    } else if (response is BaseResponse.Error) {
                        showSettingsError(response)
                    }
                }
            } else if (validationResponse is BaseResponse.Error) {
                showSettingsError(validationResponse)
            }
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            if (binding.nearbyEventsCheckbox.isChecked && binding.maxDistanceEditText.text.isNullOrEmpty()) {
                binding.maxDistanceEditText.error = "Max distance must not be empty"
                return@setOnClickListener
            }
            val isSendGeoNotifications = binding.nearbyEventsCheckbox.isChecked
            val maxDistance = if (isSendGeoNotifications) {
                binding.maxDistanceEditText.text.toString().toInt()
            } else {
                0
            }
            sessionManager.saveNotificationSettings(isSendGeoNotifications, maxDistance)
            sendNotificationsSettingsUpdateBroadcast()
            updateVolunteersDistricts()
            findNavController().navigateUp()
        }
    }

    private fun updateVolunteersDistricts() {
        val checkedPositions = binding.districtsListView.checkedItemPositions
        val selectedDistrictsGIDs = mutableSetOf<String>()
        for (i in 0 until binding.districtsListView.count) {
            if (checkedPositions.get(i)) {
                val districtName = binding.districtsListView.adapter.getItem(i) as String
                val districtGid = districts.find { it.name == districtName }?.gid
                if (districtGid != null) {
                    selectedDistrictsGIDs.add(districtGid)
                }
            }
        }

        val districtsToCreate = selectedDistrictsGIDs.subtract(previouslySelectedDistrictGIDs)
        val districtsToDelete = previouslySelectedDistrictGIDs.subtract(selectedDistrictsGIDs)
        for (districtGid in districtsToCreate) {
            val createDto = CreateVolunteersDistrictsDto(
                volunteerGID = volunteer?.gid!!,
                districtGID = districtGid
            )
            volunteersDistrictsViewModel.create("Bearer $token", createDto)
        }
        for (districtGid in districtsToDelete) {
            val volunteersDistrict = volunteersDistricts.find { it.districtGID == districtGid }
            if (volunteersDistrict != null) {
                volunteersDistrictsViewModel.deleteByGID("Bearer $token", volunteersDistrict.gid)
            }
        }
    }

    private fun sendNotificationsSettingsUpdateBroadcast() {
        val intent = Intent("com.nikitasutulov.macsro.ACTION_NOTIFICATION_SETTINGS_UPDATED")
        requireContext().sendBroadcast(intent)
    }

    private fun showSettingsError(response: BaseResponse.Error) {
        handleError(binding.root, "Error while fetching settings. ${response.error!!.message}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}