package com.nikitasutulov.macsro.ui

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

class CreateVolunteerFragment : Fragment() {
    private var _binding: FragmentCreateVolunteerBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var sessionManager: SessionManager
    private val args: CreateVolunteerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVolunteerBinding.inflate(inflater, container, false)

        initAuthViewModel()
        initVolunteerViewModel()
        initSessionManager()
        initBirthDatePicker()
        handleEmptyData()
        setSubmitVolunteerDataButtonOnClickListener()

        return binding.root
    }

    private fun initAuthViewModel() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager(requireActivity())
    }

    private fun initVolunteerViewModel() {
        volunteerViewModel = ViewModelProvider(requireActivity())[VolunteerViewModel::class.java]
    }

    @SuppressLint("DefaultLocale")
    private fun initBirthDatePicker() {
        val birthDateEditText = binding.birthdateEditText
        birthDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    birthDateEditText.setText(formattedDate)
                }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun handleEmptyData() {
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
                binding.mobilePhoneEditText.error =
                    getString(R.string.mobile_phone_must_not_be_empty)
            } else {
                binding.mobilePhoneEditText.error = null
            }
        }
    }

    private fun setSubmitVolunteerDataButtonOnClickListener() {
        binding.submitVolunteerDataButton.setOnClickListener {
            val username = args.username
            val email = args.email
            val password = args.password

            val name = binding.nameEditText.text.toString().trim()
            val secondName = binding.secondNameEditText.text.toString().trim()
            val surname = binding.surnameEditText.text.toString().trim()
            val mobilePhone = binding.mobilePhoneEditText.text.toString().trim()
            val birthdate = binding.birthdateEditText.text.toString().trim()

            val isNameValid = name.isNotBlank()
            val isSecondNameValid = secondName.isNotBlank()
            val isSurnameValid = surname.isNotBlank()
            val isMobilePhoneValid = mobilePhone.isNotBlank() && isValidPhoneNumber(mobilePhone)
            val isBirthdateValid = birthdate.isNotBlank() && isAtLeast16YearsOld(birthdate)
            if (isNameValid && isSecondNameValid && isSurnameValid && isMobilePhoneValid && isBirthdateValid) {
                val registerDto = RegisterDto(username, email, password)
                authViewModel.register(registerDto)

                authViewModel.registerResponse.observeOnce(viewLifecycleOwner) { registerResponse ->
                    if (registerResponse !is BaseResponse.Success || registerResponse.data == null) {
                        return@observeOnce
                    }

                    val userGID = registerResponse.data.toString()
                    authViewModel.login(LoginDto(username, password))

                    authViewModel.loginResponse.observeOnce(viewLifecycleOwner) { loginRes ->
                        if (loginRes !is BaseResponse.Success || loginRes.data?.token == null) {
                            return@observeOnce
                        }

                        val token = loginRes.data.token
                        val volunteerDto = CreateVolunteerDto(
                            name = name,
                            secondName = secondName,
                            surname = surname,
                            email = email,
                            mobilePhone = mobilePhone,
                            birthDate = birthdate,
                            userGID = userGID
                        )

                        volunteerViewModel.create(token, volunteerDto)

                        volunteerViewModel.createResponse.observeOnce(viewLifecycleOwner) { createRes ->
                            if (createRes is BaseResponse.Success) {
                                findNavController().navigate(R.id.action_createVolunteerFragment_to_eventsFragment)
                            }
                        }
                    }
                }
            } else {
                showValidationErrors(
                    isNameValid,
                    isSecondNameValid,
                    isSurnameValid,
                    isMobilePhoneValid,
                    isBirthdateValid
                )
            }
        }
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

    private fun showValidationErrors(
        isNameValid: Boolean,
        isSecondNameValid: Boolean,
        isSurnameValid: Boolean,
        isMobilePhoneValid: Boolean,
        isBirthdateValid: Boolean
    ) {
        if (!isNameValid) {
            binding.nameEditText.error = getString(R.string.name_must_not_be_empty)
        }
        if (!isSecondNameValid) {
            binding.secondNameEditText.error = getString(R.string.second_name_must_not_be_empty)
        }
        if (!isSurnameValid) {
            binding.surnameEditText.error = getString(R.string.surname_must_not_be_empty)
        }
        if (!isMobilePhoneValid) {
            binding.mobilePhoneEditText.error = getString(R.string.mobile_phone_must_be_valid)
        }
        if (!isBirthdateValid) {
            binding.birthdateEditText.error = getString(R.string.volunteer_age_requirement)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}