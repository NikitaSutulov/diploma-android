package com.nikitasutulov.macsro.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.GroupMember
import com.nikitasutulov.macsro.databinding.GroupMemberCardBinding

class GroupMemberAdapter(
    private val onMemberClick: ((GroupMember) -> Unit)? = null
) : ListAdapter<GroupMember, GroupMemberAdapter.GroupMemberViewHolder>(
    GroupMemberDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        val binding =
            GroupMemberCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        val groupMember = getItem(position)
        holder.bind(groupMember, onMemberClick)
    }

    class GroupMemberViewHolder(private val binding: GroupMemberCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupMember: GroupMember, onMemberClick: ((GroupMember) -> Unit)?) {
            binding.groupMemberNameTextView.text = groupMember.name
            if (groupMember.isLeader) {
                binding.groupMemberNameTextView.setTypeface(null, Typeface.BOLD)
            }
            binding.root.setOnClickListener {
                onMemberClick?.invoke(groupMember)
            }
        }
    }

    class GroupMemberDiffCallback : DiffUtil.ItemCallback<GroupMember>() {
        override fun areItemsTheSame(oldItem: GroupMember, newItem: GroupMember): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: GroupMember, newItem: GroupMember): Boolean {
            return oldItem == newItem
        }
    }
}