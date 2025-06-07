package com.nikitasutulov.macsro.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nikitasutulov.macsro.R
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.request.CreateRequestDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestPaginationQueryDto
import com.nikitasutulov.macsro.data.ui.Event
import com.nikitasutulov.macsro.data.ui.Request
import com.nikitasutulov.macsro.databinding.FragmentRequestsBinding
import com.nikitasutulov.macsro.ui.adapter.RequestAdapter
import com.nikitasutulov.macsro.utils.SessionManager
import com.nikitasutulov.macsro.utils.handleError
import com.nikitasutulov.macsro.utils.observeOnce
import com.nikitasutulov.macsro.viewmodel.operations.RequestViewModel

class RequestsFragment : Fragment() {
    private var _binding: FragmentRequestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestViewModel: RequestViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var requestAdapter: RequestAdapter
    private val args: RequestsFragmentArgs by navArgs()
    private lateinit var event: Event
    private var isRead = false
    private var isOutgoing = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestsBinding.inflate(inflater, container, false)

        initViewModels()
        initSessionManager()
        getEventInfo()
        setupRecyclerView()
        updateRequests()
        setupSwitches()
        setupCreateRequestButton()

        return binding.root
    }

    private fun initViewModels() {
        requestViewModel = ViewModelProvider(this)[RequestViewModel::class.java]
    }

    private fun initSessionManager() {
        sessionManager = SessionManager.getInstance(requireContext())
    }

    private fun getEventInfo() {
        event = args.event
        binding.eventNameTextView.text = event.name
    }

    private fun setupRecyclerView() {
        requestAdapter = RequestAdapter { request -> onMarkAsReadButtonClick(request) }
        binding.eventMessagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = requestAdapter
        }
    }

    private fun onMarkAsReadButtonClick(request: Request) {
        val token = sessionManager.getToken()
        val readLiveData = requestViewModel.read("Bearer $token", request.gid)
        readLiveData.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                updateRequests()
            } else if (response is BaseResponse.Error) {
                showMarkingRequestError(response)
            }
        }
    }

    private fun getRequests(from: String, to: String, isRead: Boolean) {
        val token = sessionManager.getToken()
        val requestsLiveModel = requestViewModel.getAll(
            "Bearer $token",
            RequestPaginationQueryDto(
                isRead = isRead,
                from = from,
                to = to,
                eventGID = event.gid
            )
        )
        requestsLiveModel.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                val requestDtos = response.data!!.items.sortedBy { it.createdAt }
                val requests = requestDtos.map {
                    val fromText =
                        if (it.from == event.dispatcherGID) getString(R.string.from_dispatcher)
                        else getString(R.string.from_coordinator)
                    val toText =
                        if (it.to == event.dispatcherGID) getString(R.string.to_dispatcher)
                        else getString(R.string.to_coordinator)
                    Request(
                        gid = it.gid,
                        from = fromText,
                        to = toText,
                        text = it.text,
                        isRead = isRead,
                        canBeRead = !isRead && it.to == event.coordinatorGID
                    )
                }
                requestAdapter.submitList(requests)
            } else if (response is BaseResponse.Error) {
                showGettingRequestsError(response)
            }
        }
    }

    private fun setupSwitches() {
        binding.showReadSwitch.setOnCheckedChangeListener { switch, _ ->
            isRead = switch.isChecked
            updateRequests()
        }
        binding.showOutgoingSwitch.setOnCheckedChangeListener { switch, _ ->
            isOutgoing = switch.isChecked
            updateRequests()
        }
    }

    private fun updateRequests() {
        if (isOutgoing) {
            getRequests(event.coordinatorGID, event.dispatcherGID, isRead)
        } else {
            getRequests(event.dispatcherGID, event.coordinatorGID, isRead)
        }
    }

    private fun setupCreateRequestButton() {
        binding.createRequestButton.setOnClickListener {
            val editText = EditText(requireContext()).apply {
                inputType = InputType.TYPE_CLASS_TEXT
                hint = context.getString(R.string.enter_your_text)
            }
            AlertDialog.Builder(requireContext())
                .setTitle(context?.getString(R.string.create_request))
                .setView(editText)
                .setPositiveButton("OK") { _, _ ->
                    val enteredText = editText.text.toString()
                    createRequest(enteredText)
                }
                .setNegativeButton(context?.getString(R.string.cancel), null)
                .show()
        }
    }

    private fun createRequest(enteredText: String) {
        val token = sessionManager.getToken()
        val requestCreationLiveData = requestViewModel.create(
            "Bearer $token",
            CreateRequestDto(
                from = event.coordinatorGID,
                to = event.dispatcherGID,
                eventGID = event.gid,
                text = enteredText,
                isRead = false
            )
        )
        requestCreationLiveData.observeOnce(viewLifecycleOwner) { response ->
            if (response is BaseResponse.Success) {
                Snackbar.make(
                    binding.root,
                    "Successfully sent the request to the dispatcher!",
                    Snackbar.LENGTH_SHORT
                ).show()
                getRequests(event.coordinatorGID, event.dispatcherGID, binding.showReadSwitch.isChecked)
            } else if (response is BaseResponse.Error) {
                showCreatingRequestError(response)
            }
        }
    }

    private fun showGettingRequestsError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to get requests. ${response.error?.message}")
    }

    private fun showMarkingRequestError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to mark the request as read. ${response.error?.message}")
    }

    private fun showCreatingRequestError(response: BaseResponse.Error) {
        handleError(binding.root, "Failed to create a request. ${response.error?.message}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
