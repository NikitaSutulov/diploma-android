package com.nikitasutulov.macsro.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.databinding.FragmentAddGoogleAuthenticatorBinding
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import androidx.core.net.toUri

class AddGoogleAuthenticatorFragment : Fragment() {
    private var _binding: FragmentAddGoogleAuthenticatorBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private val args: AddGoogleAuthenticatorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGoogleAuthenticatorBinding.inflate(inflater, container, false)

        initViewModels()
        getGoogleAuthenticatorSecret()
        setConfirmButtonOnClickListener()

        return binding.root
    }

    private fun initViewModels() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    private fun getGoogleAuthenticatorSecret() {
        authViewModel.login(LoginDto(args.username, args.password))
        authViewModel.loginResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val responseData = response.data!!
                val isLoginSuccessful = responseData.isValid
                if (!isLoginSuccessful) {
                    handleError(binding.root, "Failed to get the authenticator key. ${response.data.message}")
                } else {
                    val key = responseData.authenticatorKey
                    binding.gaSecretTextView.text = key
                    drawQrCode(key)
                    binding.confirmButton.isEnabled = true
                }
            } else if (response is BaseResponse.Error) {
                handleError(binding.root, "Failed to get the authenticator key. ${response.error!!.message}")
            }
        }
    }

    private fun drawQrCode(key: String) {
        val uri = getOtpAuthUri(key, args.username)
        val hints = mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        val qrCodeSize = binding.qrCodeImageView.height
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(uri, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints)
        val bitmap = createBitmap(qrCodeSize, qrCodeSize, Bitmap.Config.RGB_565)
        for (x in 0 until qrCodeSize) {
            for (y in 0 until qrCodeSize) {
                bitmap[x, y] =
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            }
        }
        binding.qrCodeImageView.setImageBitmap(bitmap)
        binding.qrCodeImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
            startActivity(intent)
        }
    }

    private fun getOtpAuthUri(key: String, username: String): String {
        val issuer = Uri.encode("Platform-for-coordination-of-search-and-rescue-operations")
        return "otpauth://totp/$issuer:$username?secret=$key&issuer=$issuer"
    }

    private fun setConfirmButtonOnClickListener() {
        binding.confirmButton.setOnClickListener {
            val action = AddGoogleAuthenticatorFragmentDirections
                .actionAddGoogleAuthenticatorFragmentToEnterGoogleAuthenticatorCodeFragment(args.username)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
