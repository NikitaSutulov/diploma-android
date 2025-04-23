package com.nikitasutulov.macsro.ui.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.databinding.FragmentRegisterBinding
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.viewmodel.auth.UserViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private var isCheckingUsername = false
    private var isCheckingEmail = false
    private var isUsernameAvailable = false
    private var isEmailAvailable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        setupViews()
        setupObservers()

        return binding.root
    }

    private fun setupViews() {
        binding.registerToLoginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.usernameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.usernameEditText.error = getString(R.string.empty_username_message)
            }
        }

        binding.emailEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.emailEditText.error = getString(R.string.invalid_email_message)
            }
        }

        binding.passwordEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.passwordEditText.error = getString(R.string.empty_password_message)
            }
        }

        binding.registerButton.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun setupObservers() {
        userViewModel.getWithUsernameResponse.observe(viewLifecycleOwner) { response ->
            isCheckingUsername = false
            when (response) {
                is BaseResponse.Success -> {
                    isUsernameAvailable = !response.data!!.isExist
                    if (!isUsernameAvailable) {
                        binding.usernameEditText.error = getString(R.string.username_taken_message)
                    }
                    checkAllValidationsAndNavigate()
                }
                is BaseResponse.Error -> {
                    handleError(binding.root, "Failed to check username availability. ${response.error!!.message}")
                }
                is BaseResponse.Loading -> {}
            }
            updateRegisterButtonState()
        }

        userViewModel.getWithEmailResponse.observe(viewLifecycleOwner) { response ->
            isCheckingEmail = false
            when (response) {
                is BaseResponse.Success -> {
                    isEmailAvailable = !response.data!!.isExist
                    if (!isEmailAvailable) {
                        binding.emailEditText.error = getString(R.string.email_taken_message)
                    }
                    checkAllValidationsAndNavigate()
                }
                is BaseResponse.Error -> {
                    handleError(binding.root, "Failed to check email availability. ${response.error!!.message}")
                }
                is BaseResponse.Loading -> {}
            }
            updateRegisterButtonState()
        }
    }

    private fun validateAndRegister() {
        val username = binding.usernameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        binding.usernameEditText.error = null
        binding.emailEditText.error = null
        binding.passwordEditText.error = null
        binding.confirmPasswordEditText.error = null

        var isValid = true
        if (username.isBlank()) {
            binding.usernameEditText.error = getString(R.string.empty_username_message)
            isValid = false
        }
        if (email.isBlank() || !isEmail(email)) {
            binding.emailEditText.error = getString(R.string.invalid_email_message)
            isValid = false
        }
        if (password.isBlank()) {
            binding.passwordEditText.error = getString(R.string.empty_password_message)
            isValid = false
        } else if (password != confirmPassword) {
            binding.confirmPasswordEditText.error = getString(R.string.password_not_confirmed_message)
            isValid = false
        }

        if (!isValid) return

        isCheckingUsername = true
        isCheckingEmail = true
        isUsernameAvailable = false
        isEmailAvailable = false
        updateRegisterButtonState()
        userViewModel.getWithUsername(username)
        userViewModel.getWithEmail(email)
    }

    private fun checkAllValidationsAndNavigate() {
        if (isCheckingUsername || isCheckingEmail) return

        val username = binding.usernameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()

        if (isUsernameAvailable && isEmailAvailable) {
            val action = RegisterFragmentDirections.actionRegisterFragmentToCreateVolunteerFragment(
                username,
                email,
                password
            )
            findNavController().navigate(action)
        }
    }

    private fun updateRegisterButtonState() {
        binding.registerButton.isEnabled = !isCheckingUsername && !isCheckingEmail
    }

    private fun isEmail(text: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}