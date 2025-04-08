package com.nikitasutulov.macsro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nikitasutulov.macsro.databinding.FragmentEventsBinding
import com.nikitasutulov.macsro.utils.SessionManager

class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        val sessionManager = SessionManager(requireActivity())
        val tokenAndExpiration = "Token: ${sessionManager.getToken()}\nExpiration: ${sessionManager.getExpiration()}"
        binding.helloTextView.text = tokenAndExpiration
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(requireActivity(), "Token is expired!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireActivity(), "Token is not expired!", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}