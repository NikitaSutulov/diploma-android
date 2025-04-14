package com.nikitasutulov.macsro.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nikitasutulov.macsro.databinding.FragmentCreateVolunteerBinding
import java.util.Calendar

class CreateVolunteerFragment : Fragment() {
    private var _binding: FragmentCreateVolunteerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVolunteerBinding.inflate(inflater, container, false)

        initBirthDatePicker()

        return binding.root
    }

    private fun initBirthDatePicker() {
        val birthDateEditText = binding.birthdateEditText
        birthDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(requireActivity(), { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    birthDateEditText.setText(formattedDate)
                }, year, month, day)

            datePickerDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}