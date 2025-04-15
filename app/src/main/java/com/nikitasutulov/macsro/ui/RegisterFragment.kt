package com.nikitasutulov.macsro.ui

import android.os.Bundle
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
import com.nikitasutulov.macsro.databinding.FragmentRegisterBinding
import com.nikitasutulov.macsro.repository.UserRepository
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.UserViewModel
import com.nikitasutulov.macsro.viewmodel.factories.auth.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        initUserViewModel()
        initSessionManager()
        setRegisterToLoginLinkOnClickListener()
        handleEmptyCredentials()
        setRegisterButtonOnClickListener()

        return binding.root
    }

    private fun initUserViewModel() {
        val userRepository = UserRepository()
        userViewModel = ViewModelProvider(
            requireActivity(),
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]
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
            CoroutineScope(Dispatchers.Main).launch {
                username = binding.usernameEditText.text.toString().trim()
                val email = binding.emailEditText.text.toString().trim()
                password = binding.passwordEditText.text.toString()
                val confirmPassword = binding.confirmPasswordEditText.text.toString()

                val isUsernameValid = username.isNotBlank()
                val isEmailFormatValid = email.isNotBlank() && isEmail(email)
                val isPasswordValid = password.isNotBlank()
                val isPasswordConfirmed = password == confirmPassword

                val isUsernameTaken = if (isUsernameValid) !isUsernameAvailable(username) else false
                val isEmailTaken = if (isEmailFormatValid) !isEmailAvailable(email) else false

                val isUsernameAvailable = isUsernameValid && !isUsernameTaken
                val isEmailAvailable = isEmailFormatValid && !isEmailTaken

                if (isUsernameAvailable && isEmailAvailable && isPasswordValid && isPasswordConfirmed) {
                    val action = RegisterFragmentDirections
                        .actionRegisterFragmentToCreateVolunteerFragment(username, email, password)
                    findNavController().navigate(action)
                } else {
                    showValidationErrors(
                        isUsernameValid,
                        isUsernameTaken,
                        isEmailFormatValid,
                        isEmailTaken,
                        isPasswordValid,
                        isPasswordConfirmed
                    )
                }
            }
        }
    }

    private fun isEmail(text: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    private suspend fun isUsernameAvailable(username: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            userViewModel.getWithUsername("no token", username) // TODO: remove token in API
            userViewModel.getWithUsernameResponse.observeOnce(viewLifecycleOwner) { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        val isAvailable = response.code == 204
                        continuation.resume(isAvailable) {}
                    }
                    is BaseResponse.Error -> {
                        Toast.makeText(requireActivity(), response.error!!.message, Toast.LENGTH_LONG).show()
                        binding.passwordEditText.setText("")
                        binding.passwordEditText.error = null
                        continuation.resume(false) {}
                    }
                    is BaseResponse.Loading -> {}
                }
            }
        }
    }

    private suspend fun isEmailAvailable(email: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            userViewModel.getWithEmail("no token", email) // TODO: remove token in API
            userViewModel.getWithEmailResponse.observeOnce(viewLifecycleOwner) { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        val isAvailable = response.code == 204
                        continuation.resume(isAvailable) {}
                    }
                    is BaseResponse.Error -> {
                        Toast.makeText(requireActivity(), response.error!!.message, Toast.LENGTH_LONG).show()
                        binding.passwordEditText.setText("")
                        binding.passwordEditText.error = null
                        continuation.resume(false) {}
                    }
                    is BaseResponse.Loading -> {}
                }
            }
        }
    }


    private fun showValidationErrors(
        isUsernameValid: Boolean,
        isUsernameTaken: Boolean,
        isEmailValid: Boolean,
        isEmailTaken: Boolean,
        isPasswordValid: Boolean,
        isPasswordConfirmed: Boolean
    ) {
        if (!isUsernameValid) {
            binding.usernameEditText.error = getString(R.string.empty_username_message)
        } else if (isUsernameTaken) {
            binding.usernameEditText.error = getString(R.string.username_taken_message)
        }

        if (!isEmailValid) {
            binding.emailEditText.error = getString(R.string.invalid_email_message)
        } else if (isEmailTaken) {
            binding.emailEditText.error = getString(R.string.email_taken_message)
        }

        if (!isPasswordValid) {
            binding.passwordEditText.error = getString(R.string.empty_password_message)
        }

        if (isPasswordValid && !isPasswordConfirmed) {
            binding.confirmPasswordEditText.error = getString(R.string.password_not_confirmed_message)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}