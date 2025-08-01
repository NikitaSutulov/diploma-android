package com.nikitasutulov.macsro.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.databinding.FragmentCreateVolunteerBinding
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class CreateVolunteerFragment : Fragment() {
    private var _binding: FragmentCreateVolunteerBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var sessionManager: SessionManager

    private var isProcessing = false
    private var userGID: String? = null
    private var email: String? = null
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVolunteerBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        checkIfVolunteerCreationNeeded()
        setupViews()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        volunteerViewModel = ViewModelProvider(requireActivity())[VolunteerViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun checkIfVolunteerCreationNeeded() {
        token = sessionManager.getToken()
        authViewModel.validateToken("Bearer $token")
        authViewModel.tokenValidationResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val responseData = response.data!!
                val user = responseData.user!!
                if (user.roles.any { it.name == "Coordinator" }) {
                    navigateToEvents()
                } else {
                    volunteerViewModel.getByUserGID("Bearer $token", user.id)
                    observeGettingVolunteerDto(user)
                }
            } else if (response is BaseResponse.Error) {
                handleVolunteerCreationError(response.error!!.message)
            }
        }
    }

    private fun observeGettingVolunteerDto(user: UserDto) {
        volunteerViewModel.getByUserGIDResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                if (response.code != 204) {
                    navigateToEvents()
                } else {
                    userGID = user.id
                    email = user.email
                }
            } else if (response is BaseResponse.Error) {
                handleVolunteerCreationError(response.error!!.message)
            }
        }
    }

    private fun navigateToEvents() {
        findNavController().navigate(R.id.action_createVolunteerFragment_to_eventsFragment)
    }

    private fun setupViews() {
        setupBirthDatePicker()
        setupInputValidation()
        setupSubmitButton()
    }

    @SuppressLint("DefaultLocale")
    private fun setupBirthDatePicker() {
        binding.birthdateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -16)
            DatePickerDialog(requireActivity(), { _, year, month, day ->
                binding.birthdateEditText.setText(String.format("%02d/%02d/%04d", day, month + 1, year))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupInputValidation() {
        binding.nameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.nameEditText.error = getString(R.string.name_must_not_be_empty)
            } else {
                binding.nameEditText.error = null
            }
        }

        binding.secondNameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.secondNameEditText.error = getString(R.string.second_name_must_not_be_empty)
            } else {
                binding.secondNameEditText.error = null
            }
        }

        binding.surnameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.surnameEditText.error = getString(R.string.surname_must_not_be_empty)
            } else {
                binding.surnameEditText.error = null
            }
        }

        binding.mobilePhoneEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.mobilePhoneEditText.error = getString(R.string.mobile_phone_must_not_be_empty)
            } else {
                binding.mobilePhoneEditText.error = null
            }
        }
    }

    private fun setupSubmitButton() {
        binding.submitVolunteerDataButton.setOnClickListener {
            if (isProcessing) return@setOnClickListener

            if (validateAllInputs()) {
                isProcessing = true
                updateButtonState()
                createVolunteerRecord()
            }
        }
    }

    private fun validateAllInputs(): Boolean {
        val name = binding.nameEditText.text.toString().trim()
        val secondName = binding.secondNameEditText.text.toString().trim()
        val surname = binding.surnameEditText.text.toString().trim()
        val mobilePhone = binding.mobilePhoneEditText.text.toString().trim()
        val birthdate = binding.birthdateEditText.text.toString().trim()

        var isValid = true

        if (name.isBlank()) {
            binding.nameEditText.error = getString(R.string.name_must_not_be_empty)
            isValid = false
        }

        if (secondName.isBlank()) {
            binding.secondNameEditText.error = getString(R.string.second_name_must_not_be_empty)
            isValid = false
        }

        if (surname.isBlank()) {
            binding.surnameEditText.error = getString(R.string.surname_must_not_be_empty)
            isValid = false
        }

        if (mobilePhone.isBlank()) {
            binding.mobilePhoneEditText.error = getString(R.string.mobile_phone_must_not_be_empty)
            isValid = false
        } else if (!isValidPhoneNumber(mobilePhone)) {
            binding.mobilePhoneEditText.error = getString(R.string.mobile_phone_must_be_valid)
            isValid = false
        }

        if (birthdate.isBlank()) {
            binding.birthdateEditText.error = getString(R.string.birthdate_must_not_be_empty)
            isValid = false
        } else if (!isAtLeast16YearsOld(birthdate)) {
            binding.birthdateEditText.error = getString(R.string.volunteer_age_requirement)
            isValid = false
        }

        return isValid
    }

    private fun createVolunteerRecord() {
        val volunteerDto = CreateVolunteerDto(
            name = binding.nameEditText.text.toString().trim(),
            secondName = binding.secondNameEditText.text.toString().trim(),
            surname = binding.surnameEditText.text.toString().trim(),
            email = email!!,
            mobilePhone = binding.mobilePhoneEditText.text.toString().trim(),
            ratingNumber = 0,
            birthDate = convertDateInputToIsoFormat(binding.birthdateEditText.text.toString()),
            userGID = userGID!!
        )

        volunteerViewModel.create("Bearer ${token!!}", volunteerDto)
        volunteerViewModel.createResponse.observeOnce(viewLifecycleOwner) { response ->
            isProcessing = false
            updateButtonState()

            if (response is BaseResponse.Success) {
                navigateToEvents()
            } else if (response is BaseResponse.Error) {
                handleVolunteerCreationError("Volunteer creation failed: ${response.error?.message}")
            }
        }
    }

    private fun handleVolunteerCreationError(message: String) {
        isProcessing = false
        updateButtonState()
        handleError(binding.root, message)
    }

    private fun updateButtonState() {
        binding.submitVolunteerDataButton.isEnabled = !isProcessing
    }

    private fun isValidPhoneNumber(mobilePhone: String): Boolean {
        val phoneRegex = Regex("^\\+380(50|63|66|67|68|73|75|77|93|95|96|97|98|99)\\d{7}\$")
        return phoneRegex.matches(mobilePhone)
    }

    private fun isAtLeast16YearsOld(birthdateString: String): Boolean {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.isLenient = false
        return try {
            val birthDate = formatter.parse(birthdateString) ?: return false
            val sixteenYearsAgo = Calendar.getInstance().apply {
                add(Calendar.YEAR, -16)
            }
            !birthDate.after(sixteenYearsAgo.time)
        } catch (e: ParseException) {
            false
        }
    }

    private fun convertDateInputToIsoFormat(input: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        inputFormat.isLenient = false
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(input)
        return outputFormat.format(date!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}