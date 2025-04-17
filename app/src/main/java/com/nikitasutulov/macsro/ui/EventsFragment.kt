package com.nikitasutulov.macsro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nikitasutulov.macsro.R
import androidx.navigation.fragment.findNavController
import com.nikitasutulov.macsro.databinding.FragmentEventsBinding
import com.nikitasutulov.macsro.utils.SessionManager

class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        sessionManager = SessionManager.getInstance(requireContext())
        setupLogoutButton()

        return binding.root
    }

    private fun checkAuthState() {
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            sessionManager.clearSession()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        binding.root.post { findNavController().navigate(R.id.loginFragment) }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}