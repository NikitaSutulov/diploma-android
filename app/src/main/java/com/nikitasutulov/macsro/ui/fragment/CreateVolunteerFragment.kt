package com.nikitasutulov.macsro.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.databinding.FragmentCreateVolunteerBinding
import com.nikitasutulov.macsro.utils.SessionManager
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
    private val args: CreateVolunteerFragmentArgs by navArgs()

    private var isProcessing = false
    private var userGID: String? = null
    private var authToken: String? = null
    private var tokenExpiration: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVolunteerBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        setupViews()
        setupObservers()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        volunteerViewModel = ViewModelProvider(requireActivity())[VolunteerViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
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
                startRegistrationProcess()
            }
        }
    }

    private fun setupObservers() {
        authViewModel.registerResponse.observeOnce(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    userGID = response.data!!.id
                    performLogin()
                }
                is BaseResponse.Error -> {
                    handleError("Registration failed: ${response.error?.message}")
                }
                is BaseResponse.Loading -> {}
            }
        }

        authViewModel.loginResponse.observeOnce(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    authToken = response.data?.token
                    tokenExpiration = response.data?.expiration
                    createVolunteerRecord()
                }
                is BaseResponse.Error -> {
                    handleError("Login failed: ${response.error?.message}")
                }
                is BaseResponse.Loading -> {}
            }
        }

        volunteerViewModel.createResponse.observeOnce(viewLifecycleOwner) { response ->
            isProcessing = false
            updateButtonState()

            when (response) {
                is BaseResponse.Success -> {
                    saveSessionAndNavigate()
                }
                is BaseResponse.Error -> {
                    handleError("Volunteer creation failed: ${response.error?.message}")
                }
                is BaseResponse.Loading -> {}
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

    private fun startRegistrationProcess() {
        authViewModel.register(RegisterDto(
            username = args.username,
            email = args.email,
            password = args.password
        ))
    }

    private fun performLogin() {
        authViewModel.login(LoginDto(
            username = args.username,
            password = args.password
        ))
    }

    private fun createVolunteerRecord() {
        val volunteerDto = CreateVolunteerDto(
            name = binding.nameEditText.text.toString().trim(),
            secondName = binding.secondNameEditText.text.toString().trim(),
            surname = binding.surnameEditText.text.toString().trim(),
            email = args.email,
            mobilePhone = binding.mobilePhoneEditText.text.toString().trim(),
            birthDate = convertDateInputToIsoFormat(binding.birthdateEditText.text.toString()),
            userGID = userGID!!
        )

        volunteerViewModel.create("Bearer ${authToken!!}", volunteerDto)
    }

    private fun saveSessionAndNavigate() {
        authToken?.let { token ->
            tokenExpiration?.let { expiration ->
                sessionManager.saveToken(token, expiration)
                findNavController().navigate(R.id.action_createVolunteerFragment_to_eventsFragment)
            }
        }
    }

    private fun handleError(message: String) {
        isProcessing = false
        updateButtonState()
        com.nikitasutulov.macsro.utils.handleError(binding.root, message)
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