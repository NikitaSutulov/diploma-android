package com.nikitasutulov.macsro.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.firestore.Query
import com.nikitasutulov.macsro.data.ui.ChatMessage
import com.nikitasutulov.macsro.databinding.FragmentChatBinding
import com.nikitasutulov.macsro.ui.adapter.ChatMessageAdapter

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var adapter: ChatMessageAdapter
    private val db = FirebaseFirestore.getInstance()
    private var messagesListener: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        val roomId = args.roomId
        val userGID = args.userGID
        val roomName = args.roomName
        val username = args.username

        binding.chatRoomTitle.text = roomName

        adapter = ChatMessageAdapter(userGID)
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messagesRecyclerView.adapter = adapter

        observeMessages(roomId)

        binding.sendButton.setOnClickListener {
            val text = binding.messageEditText.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(roomId, userGID, username, text)
                binding.messageEditText.setText("")
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic(roomId)

        return binding.root
    }

    private fun observeMessages(roomId: String) {
        messagesListener = db.collection("chatRooms")
            .document(roomId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, _ ->
                if (snapshots != null) {
                    val messages =
                        snapshots.documents.mapNotNull { it.toObject(ChatMessage::class.java) }
                    adapter.submitList(messages)
                    binding.messagesRecyclerView.scrollToPosition(messages.size - 1)
                }
            }
    }

    private fun sendMessage(roomId: String, userId: String, username: String, text: String) {
        val message = ChatMessage(
            senderGID = userId,
            senderUsername = username,
            text = text,
            timestamp = Timestamp.now()
        )
        db.collection("chatRooms")
            .document(roomId)
            .collection("messages")
            .add(message)
    }

    override fun onResume() {
        super.onResume()
        requireContext().getSharedPreferences("chat", Context.MODE_PRIVATE)
            .edit()
            .putString("currentRoomId", args.roomId)
            .apply()
    }

    override fun onPause() {
        super.onPause()
        requireContext().getSharedPreferences("chat", Context.MODE_PRIVATE)
            .edit()
            .remove("currentRoomId")
            .apply()
        messagesListener?.remove()
        messagesListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
