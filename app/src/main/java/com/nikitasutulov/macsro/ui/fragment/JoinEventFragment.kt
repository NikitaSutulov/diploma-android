package com.nikitasutulov.macsro.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.databinding.FragmentJoinEventBinding
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.qr.QrCodeViewModel

class JoinEventFragment : Fragment() {
    private var _binding: FragmentJoinEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var qrCodeViewModel: QrCodeViewModel
    private lateinit var sessionManager: SessionManager
    private val args: JoinEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJoinEventBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        requestQrCode()
        setupDoneButton()

        return binding.root
    }

    private fun initViewModels() {
        qrCodeViewModel = ViewModelProvider(this)[QrCodeViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun requestQrCode() {
        val token = sessionManager.getToken()
        qrCodeViewModel.generateQrCode(
            "Bearer $token",
            CreateVolunteersEventsDto(args.volunteerGID, args.eventGID)
        )
        qrCodeViewModel.generateQrCodeResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val qrCodeInBase64 = response.data!!.qrBase64
                drawQrCode(qrCodeInBase64)
                binding.doneButton.isEnabled = true
            } else if (response is BaseResponse.Error) {
                handleError(
                    binding.root,
                    "Failed to get QR Code from the server. ${response.error!!.message}"
                )
            }
        }
    }

    private fun drawQrCode(qrCodeInBase64: String) {
        val qrCodeBytesDecoded = Base64.decode(qrCodeInBase64, Base64.DEFAULT)
        val qrCodeImageBitmap =
            BitmapFactory.decodeByteArray(qrCodeBytesDecoded, 0, qrCodeBytesDecoded.size)
        binding.qrCodeImageView.setImageBitmap(qrCodeImageBitmap)
    }

    private fun setupDoneButton() {
        binding.doneButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
