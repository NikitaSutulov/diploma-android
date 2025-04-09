package com.nikitasutulov.macsro.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.databinding.FragmentLoginBinding
import com.nikitasutulov.macsro.repository.AuthRepository
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.viewmodel.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.factories.auth.AuthViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initAuthViewModel()
        initSessionManager()
        handleTogglingPasswordVisibility()
        handleEmptyCredentials()
        setLoginResponseObserver()
        setLoginButtonOnClickListener()

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

    private fun handleTogglingPasswordVisibility() {
        val hiddenPasswordInputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        val shownPasswordInputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.togglePasswordVisibilityButton.setOnClickListener {
            val currentPasswordInputType = binding.passwordEditText.inputType
            if (currentPasswordInputType == hiddenPasswordInputType) {
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

    private fun handleEmptyCredentials() {
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
                binding.usernameEditText.error = null
            }
        }
    }

    private fun setLoginButtonOnClickListener() {
        binding.loginButton.setOnClickListener {
            val isUsernameEntered = !binding.usernameEditText.text.isNullOrBlank()
            val isPasswordEntered = !binding.passwordEditText.text.isNullOrBlank()
            if (isUsernameEntered && isPasswordEntered) {
                val username = binding.usernameEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()
                CoroutineScope(Dispatchers.IO).launch {
                    authViewModel.login(LoginDto(username, password))
                }
            } else {
                if (!isUsernameEntered) {
                    binding.usernameEditText.error = getString(R.string.empty_username_message)
                }
                if (!isPasswordEntered) {
                    binding.passwordEditText.error = getString(R.string.empty_password_message)
                }
            }
        }
    }

    private fun setLoginResponseObserver() {
        authViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    Log.v("Login", "Login successful")
                    val responseData = response.data!!
                    sessionManager.saveToken(responseData.token, responseData.expiration)
                    findNavController().navigate(R.id.action_loginFragment_to_eventsFragment)
                }
                is BaseResponse.Error -> {
                    val errorMessage = response.error!!.message
                    Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_LONG)
                        .show()
                    binding.passwordEditText.setText("")
                    binding.passwordEditText.error = null
                }
                is BaseResponse.Loading -> {
                    Log.v("Login", "Pending login...")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
