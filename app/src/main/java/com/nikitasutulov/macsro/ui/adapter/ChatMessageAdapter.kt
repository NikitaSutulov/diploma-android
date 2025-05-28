package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.ChatMessage
import com.nikitasutulov.macsro.databinding.MessageCardBinding
import com.nikitasutulov.macsro.databinding.OwnMessageCardBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ChatMessageAdapter(private val currentUserId: String) :
    ListAdapter<ChatMessage, RecyclerView.ViewHolder>(ChatMessageDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_OWN = 0
        private const val VIEW_TYPE_OTHER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderGID == currentUserId) VIEW_TYPE_OWN else VIEW_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_OWN -> {
                val binding = OwnMessageCardBinding.inflate(inflater, parent, false)
                OwnMessageViewHolder(binding)
            }
            else -> {
                val binding = MessageCardBinding.inflate(inflater, parent, false)
                OtherMessageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is OwnMessageViewHolder -> holder.bind(message)
            is OtherMessageViewHolder -> holder.bind(message)
        }
    }

    inner class OwnMessageViewHolder(
        private val binding: OwnMessageCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.messageTextView.text = message.text
            binding.timestampTextView.text = message.timestamp?.toDate()?.let {
                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it)
            }
        }
    }

    inner class OtherMessageViewHolder(
        private val binding: MessageCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.senderTextView.text = message.senderUsername
            binding.messageTextView.text = message.text
            binding.timestampTextView.text = message.timestamp?.toDate()?.let {
                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it)
            }
        }
    }

    class ChatMessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }
}
