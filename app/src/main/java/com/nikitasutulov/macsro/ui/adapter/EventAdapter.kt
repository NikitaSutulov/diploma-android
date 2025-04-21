package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.Event
import com.nikitasutulov.macsro.databinding.EventCardBinding

class EventAdapter(private val onItemClick: (Event) -> Unit) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }

    class EventViewHolder(private val binding: EventCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, onItemClick: (Event) -> Unit) {
            binding.eventNameTextView.text = event.name
            binding.eventTypeTextView.text = event.eventType.name
            binding.eventStatusTextView.text = event.eventStatus.name
            binding.eventDistrictTextView.text = event.district.name

            binding.root.setOnClickListener { onItemClick(event) }
        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}