package com.nikitasutulov.macsro.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.VolunteerRating
import com.nikitasutulov.macsro.databinding.VolunteerRatingCardBinding

class VolunteerRatingAdapter :
    ListAdapter<VolunteerRating, VolunteerRatingAdapter.VolunteerRatingViewHolder>(
        VolunteerRatingDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerRatingViewHolder {
        val binding =
            VolunteerRatingCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VolunteerRatingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VolunteerRatingViewHolder, position: Int) {
        val volunteerRating = getItem(position)
        holder.bind(volunteerRating)
    }

    class VolunteerRatingViewHolder(private val binding: VolunteerRatingCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(volunteerRating: VolunteerRating) {
            binding.volunteerNameTextView.text = volunteerRating.name
            binding.volunteerRatingTextView.text = volunteerRating.ratingNumber.toString()
            binding.volunteerPositionTextView.text = volunteerRating.position.toString()
            if (volunteerRating.isOfCurrentUser) {
                binding.volunteerPositionTextView.setTypeface(null, Typeface.BOLD)
                binding.volunteerNameTextView.setTypeface(null, Typeface.BOLD)
                binding.volunteerRatingTextView.setTypeface(null, Typeface.BOLD)
            } else {
                binding.volunteerPositionTextView.setTypeface(null, Typeface.NORMAL)
                binding.volunteerNameTextView.setTypeface(null, Typeface.NORMAL)
                binding.volunteerRatingTextView.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    class VolunteerRatingDiffCallback : DiffUtil.ItemCallback<VolunteerRating>() {
        override fun areItemsTheSame(oldItem: VolunteerRating, newItem: VolunteerRating): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: VolunteerRating, newItem: VolunteerRating): Boolean {
            return oldItem == newItem
        }
    }
}
