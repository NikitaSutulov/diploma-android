package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.Group
import com.nikitasutulov.macsro.data.ui.GroupMember
import com.nikitasutulov.macsro.databinding.GroupCardBinding

class GroupAdapter :
    ListAdapter<Group, GroupAdapter.GroupViewHolder>(
        GroupDiffCallback()
    ) {
    private var onMemberClick: ((GroupMember) -> Unit)? = null

    fun setOnMemberClickListener(listener: (GroupMember) -> Unit) {
        onMemberClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding =
            GroupCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding, onMemberClick)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }

    class GroupViewHolder(
        private val binding: GroupCardBinding,
        private val onMemberClick: ((GroupMember) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group) {
            binding.groupNameTextView.text = group.name
            val membersAdapter = GroupMemberAdapter(onMemberClick)
            with(binding.groupMembersRecyclerView) {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = membersAdapter
            }
            membersAdapter.submitList(group.members)
        }
    }

    class GroupDiffCallback : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem == newItem
        }
    }
}