package com.nikitasutulov.macsro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.GetTokenDto
import com.nikitasutulov.macsro.databinding.FragmentEnterGoogleAuthenticatorCodeBinding
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel

class EnterGoogleAuthenticatorCodeFragment : Fragment() {
    private var _binding: FragmentEnterGoogleAuthenticatorCodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sessionManager: SessionManager
    private val args: EnterGoogleAuthenticatorCodeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterGoogleAuthenticatorCodeBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        setListeners()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun setListeners() {
        setCodeTextChangedListener()
        setSubmitButtonOnClickListener()
    }

    private fun setCodeTextChangedListener() {
        val authenticatorCodeLength = 6
        binding.gaCodeEditText.addTextChangedListener {
            binding.submitCodeButton.isEnabled = it?.length == authenticatorCodeLength
        }
    }

    private fun setSubmitButtonOnClickListener() {
        binding.submitCodeButton.setOnClickListener {
            val code = binding.gaCodeEditText.text.toString()
            authViewModel.getToken(GetTokenDto(code, args.username))
            authViewModel.getTokenResponse.observeOnce(viewLifecycleOwner) { response ->
                if (response is BaseResponse.Success) {
                    val responseData = response.data!!
                    sessionManager.saveToken(responseData.token, responseData.expiration)
                    findNavController().navigate(R.id.action_enterGoogleAuthenticatorCodeFragment_to_createVolunteerFragment)
                } else if (response is BaseResponse.Error) {
                    showCodeError(response)
                }
            }
        }
    }

    private fun showCodeError(response: BaseResponse.Error) {
        handleError(binding.root,
            getString(R.string.failed_to_authenticate, response.error?.message))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
