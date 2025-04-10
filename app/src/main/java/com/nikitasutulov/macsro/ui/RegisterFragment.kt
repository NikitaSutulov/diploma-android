package com.nikitasutulov.macsro.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.databinding.FragmentRegisterBinding
import com.nikitasutulov.macsro.repository.AuthRepository
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.viewmodel.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.factories.auth.AuthViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        initAuthViewModel()
        initSessionManager()
        setRegisterToLoginLinkOnClickListener()
        handleEmptyCredentials()
        setRegisterResponseObserver()
        setRegisterButtonOnClickListener()

        return binding.root
    }

    private fun initAuthViewModel() {
        val authRepository = AuthRepository()
        authViewModel = ViewModelProvider(
            requireActivity(),
            AuthViewModelFactory(authRepository)
        )[AuthViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager(requireActivity())
    }

    private fun setRegisterToLoginLinkOnClickListener() {
        binding.registerToLoginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun handleEmptyCredentials() {
        binding.usernameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.usernameEditText.error = getString(R.string.empty_username_message)
            } else {
                binding.usernameEditText.error = null
            }
        }
        binding.emailEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.emailEditText.error = getString(R.string.invalid_email_message)
            } else {
                binding.emailEditText.error = null
            }
        }
        binding.passwordEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.passwordEditText.error = getString(R.string.empty_password_message)
            } else {
                binding.usernameEditText.error = null
            }
        }
    }

    private fun setRegisterButtonOnClickListener() {
        binding.registerButton.setOnClickListener {
            username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()
            val isUsernameValid = username.isNotBlank()
            val isEmailValid = email.isNotBlank() && isEmail(email)
            val isPasswordValid = password.isNotBlank()
            val isPasswordConfirmed = password == confirmPassword
            if (isUsernameValid && isEmailValid && isPasswordValid && isPasswordConfirmed) {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.v("Register", "Calling register")
                    authViewModel.register(RegisterDto(username, email, password))
                }
            } else {
                showValidationErrors(
                    isUsernameValid,
                    isEmailValid,
                    isPasswordValid,
                    isPasswordConfirmed
                )
            }
        }
    }

    private fun isEmail(text: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    private fun showValidationErrors(
        isUsernameValid: Boolean,
        isEmailValid: Boolean,
        isPasswordValid: Boolean,
        isPasswordConfirmed: Boolean
    ) {
        if (!isUsernameValid) {
            binding.usernameEditText.error = getString(R.string.empty_username_message)
        }
        if (!isEmailValid) {
            binding.emailEditText.error = getString(R.string.invalid_email_message)
        }
        if (!isPasswordValid) {
            binding.passwordEditText.error = getString(R.string.empty_password_message)
        }
        if (isPasswordValid && !isPasswordConfirmed) {
            binding.confirmPasswordEditText.error =
                getString(R.string.password_not_confirmed_message)
        }
    }

    private fun setRegisterResponseObserver() {
        authViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    Log.v("Register", "Register successful")
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(
                        username,
                        password
                    )
                    findNavController().navigate(action)
                }

                is BaseResponse.Error -> {
                    val errorMessage = response.error!!.message
                    Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_LONG)
                        .show()
                    binding.passwordEditText.setText("")
                    binding.confirmPasswordEditText.setText("")
                    binding.passwordEditText.error = null
                }

                is BaseResponse.Loading -> {
                    Log.v("Register", "Pending register...")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}