package com.nikitasutulov.macsro.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.databinding.FragmentLoginBinding
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sessionManager: SessionManager
    private var isLoggingIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initDependencies()
        setupViews()

        return binding.root
    }

    private fun initDependencies() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun setupViews() {
        setupLoginToRegisterLink()
        setupPasswordVisibilityToggle()
        setupInputValidation()
        setupLoginButton()
    }

    private fun setupLoginToRegisterLink() {
        binding.loginToRegisterLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupPasswordVisibilityToggle() {
        val hiddenPasswordInputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        val shownPasswordInputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.togglePasswordVisibilityButton.setOnClickListener {
            val currentInputType = binding.passwordEditText.inputType
            if (currentInputType == hiddenPasswordInputType) {
                binding.passwordEditText.inputType = shownPasswordInputType
                binding.togglePasswordVisibilityButton.setImageResource(R.drawable.outline_eye_disabled_24)
            } else {
                binding.passwordEditText.inputType = hiddenPasswordInputType
                binding.togglePasswordVisibilityButton.setImageResource(R.drawable.baseline_eye_24)
            }
            binding.passwordEditText.typeface = binding.usernameEditText.typeface
            binding.passwordEditText.setSelection(binding.passwordEditText.text?.length ?: 0)
        }
    }

    private fun setupInputValidation() {
        binding.usernameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.usernameEditText.error = getString(R.string.empty_username_message)
            } else {
                binding.usernameEditText.error = null
            }
        }

        binding.passwordEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.passwordEditText.error = getString(R.string.empty_password_message)
            } else {
                binding.passwordEditText.error = null
            }
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        if (isLoggingIn) return
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        binding.usernameEditText.error = null
        binding.passwordEditText.error = null
        val isUsernameValid = username.isNotBlank()
        val isPasswordValid = password.isNotBlank()
        if (!isUsernameValid || !isPasswordValid) {
            showValidationErrors(isUsernameValid, isPasswordValid)
            return
        }
        isLoggingIn = true
        authViewModel.login(LoginDto(username, password))
        authViewModel.loginResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val responseData = response.data!!
                val isLoginSuccessful = responseData.isValid
                if (!isLoginSuccessful) {
                    showLoginError(response.data.message)
                } else {
                    authViewModel.clearLoginResponse()
                    val action = LoginFragmentDirections.actionLoginFragmentToEnterGoogleAuthenticatorCodeFragment(username)
                    findNavController().navigate(action)
                }
            } else if (response is BaseResponse.Error) {
                showLoginError(response.error!!.message)
            }
        }
    }

    private fun showValidationErrors(
        isUsernameValid: Boolean,
        isPasswordValid: Boolean
    ) {
        if (!isUsernameValid) {
            binding.usernameEditText.error = getString(R.string.empty_username_message)
        }
        if (!isPasswordValid) {
            binding.passwordEditText.error = getString(R.string.empty_password_message)
        }
    }

    private fun showLoginError(message: String) {
        handleError(binding.root, "Failed to login. $message")
        binding.passwordEditText.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
