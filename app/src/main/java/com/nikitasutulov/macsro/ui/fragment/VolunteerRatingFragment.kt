package com.nikitasutulov.macsro.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.ui.VolunteerRating
import com.nikitasutulov.macsro.databinding.FragmentRatingBinding
import com.nikitasutulov.macsro.ui.adapter.VolunteerRatingAdapter
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel

class VolunteerRatingFragment : Fragment() {
    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!
    private lateinit var volunteerViewModel: VolunteerViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var volunteerRatingAdapter: VolunteerRatingAdapter
    private val args: VolunteerRatingFragmentArgs by navArgs()
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        initRecyclerViews()
        getUserVolunteerRating()
        getVolunteersRating(0)
        setupButtons()

        return binding.root
    }

    private fun initViewModels() {
        volunteerViewModel = ViewModelProvider(this)[VolunteerViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun initRecyclerViews() {
        volunteerRatingAdapter = VolunteerRatingAdapter()
        binding.volunteersRatingRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = volunteerRatingAdapter
        }
    }

    private fun getUserVolunteerRating() {
        val token = sessionManager.getToken()
        val userGID = args.userGID
        volunteerViewModel.getByUserGID("Bearer $token", userGID)
        volunteerViewModel.getByUserGIDResponse.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val volunteerDto = response.data!!
                renderUserVolunteerRating(volunteerDto)
            } else if (response is BaseResponse.Error) {
                showUserRatingError(response)
            }
        }
    }

    private fun renderUserVolunteerRating(volunteerDto: VolunteerDto) {
        binding.volunteerNameTextView.text = volunteerDto.name
        binding.volunteerRatingTextView.text = volunteerDto.ratingNumber.toString()
    }

    private fun getVolunteersRating(pageDiff: Int) {
        val token = sessionManager.getToken()
        val newPage = currentPage + pageDiff
        val currentPageLiveData = volunteerViewModel.getAll(
            "Bearer $token",
            newPage,
            PAGE_SIZE,
            "ratingNumber",
            true
        )
        currentPageLiveData.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val volunteerDtos = response.data!!
                if (volunteerDtos.isEmpty() && pageDiff > 0) {
                    binding.nextPageButton.isEnabled = false
                    return@observeOnce
                }
                val currentUserGID = args.userGID
                val volunteerRatings = volunteerDtos.mapIndexed { index, it ->
                    VolunteerRating(
                        gid = it.gid,
                        name = "${it.name} ${it.surname}",
                        ratingNumber = it.ratingNumber,
                        isOfCurrentUser = it.userGID == currentUserGID,
                        place = PAGE_SIZE * (newPage - 1) + index + 1
                    )
                }
                volunteerRatingAdapter.submitList(volunteerRatings)
                currentPage = newPage
                binding.pageNumberTextView.text = currentPage.toString()
                binding.volunteersRatingRecyclerView.scrollToPosition(0)
                updateButtonsAvailability(volunteerRatings.size)
                if (volunteerRatings.size == PAGE_SIZE) {
                    checkIfNextPageExists(token, newPage + 1)
                }
            } else if (response is BaseResponse.Error) {
                showVolunteersRatingError(response)
            }
        }
    }

    private fun checkIfNextPageExists(token: String?, nextPage: Int) {
        val firstItemOfNextPagePlace = PAGE_SIZE * (nextPage - 1) + 1
        val nextPageLiveData = volunteerViewModel.getAll(
            "Bearer $token",
            firstItemOfNextPagePlace,
            1,
            "ratingNumber",
            true
        )
        nextPageLiveData.observeOnce(viewLifecycleOwner) { response->
            if (response is BaseResponse.Success) {
                val isNextPageExists = response.data!!.isNotEmpty()
                if (!isNextPageExists) {
                    binding.nextPageButton.isEnabled = false
                }
            } else if (response is BaseResponse.Error) {
                showVolunteersRatingError(response)
            }
        }
    }

    private fun setupButtons() {
        setupPagingButtons()
        setupRefreshButton()
    }

    private fun setupPagingButtons() {
        binding.previousPageButton.setOnClickListener {
            getVolunteersRating(-1)
        }
        binding.nextPageButton.setOnClickListener {
            getVolunteersRating(1)
        }
    }

    private fun setupRefreshButton() {
        binding.refreshButton.setOnClickListener {
            getVolunteersRating(0)
        }
    }

    private fun updateButtonsAvailability(receivedItemCount: Int) {
        binding.previousPageButton.isEnabled = currentPage > 1
        binding.nextPageButton.isEnabled = receivedItemCount == PAGE_SIZE
    }

    private fun showUserRatingError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get user's volunteer rating. ${response.error?.message}")
    }

    private fun showVolunteersRatingError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get volunteers rating list. ${response.error?.message}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
