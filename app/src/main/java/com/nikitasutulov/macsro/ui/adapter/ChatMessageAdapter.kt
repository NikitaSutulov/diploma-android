package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.ChatMessage

class ChatMessageAdapter(private val currentUserId: String) :
    ListAdapter<ChatMessage, ChatMessageAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = getItem(position)
        holder.textView.text = if (message.senderGID == currentUserId) {
            "[${message.timestamp?.toDate()}]\nYou: ${message.text}\n"
        } else {
            "[${message.timestamp?.toDate()}]\n${message.senderUsername}: ${message.text}\n"
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ChatMessage>() {
            override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = false
            override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = false
        }
    }
}