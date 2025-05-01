package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.EventResource
import com.nikitasutulov.macsro.databinding.EventResourceCardBinding

class EventResourceAdapter :
    ListAdapter<EventResource, EventResourceAdapter.EventResourceViewHolder>(
        EventResourceDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventResourceViewHolder {
        val binding =
            EventResourceCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventResourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventResourceViewHolder, position: Int) {
        val eventResource = getItem(position)
        holder.bind(eventResource)
    }

    class EventResourceViewHolder(private val binding: EventResourceCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventResource: EventResource) {
            binding.eventResourceNameTextView.text = eventResource.resourceName
            val quantityWithMeasurementUnit =
                "${eventResource.availableQuantity}/${eventResource.requiredQuantity} ${eventResource.measurementUnitName}"
//                "${eventResource.availableQuantity}/${eventResource.requiredQuantity}"
            binding.eventResourceQuantityTextView.text = quantityWithMeasurementUnit
        }
    }

    class EventResourceDiffCallback : DiffUtil.ItemCallback<EventResource>() {
        override fun areItemsTheSame(oldItem: EventResource, newItem: EventResource): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: EventResource, newItem: EventResource): Boolean {
            return oldItem == newItem
        }
    }
}