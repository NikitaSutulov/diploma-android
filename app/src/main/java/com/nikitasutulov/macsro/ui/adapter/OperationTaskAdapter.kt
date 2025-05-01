package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.OperationTask
import com.nikitasutulov.macsro.databinding.OperationTaskCardBinding

class OperationTaskAdapter() : ListAdapter<OperationTask, OperationTaskAdapter.OperationTaskViewHolder>(OperationTaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationTaskViewHolder {
        val binding = OperationTaskCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OperationTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OperationTaskViewHolder, position: Int) {
        val operationTask = getItem(position)
        holder.bind(operationTask)
    }

    class OperationTaskViewHolder(private val binding: OperationTaskCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(operationTask: OperationTask) {
            binding.operationTaskNameTextView.text = operationTask.name
            binding.operationTaskDescriptionTextView.text = operationTask.taskDescription
            binding.operationTaskStatusTextView.text = operationTask.taskStatusName
            binding.operationTaskGroupNameTextView.text = operationTask.groupName
        }
    }

    class OperationTaskDiffCallback : DiffUtil.ItemCallback<OperationTask>() {
        override fun areItemsTheSame(oldItem: OperationTask, newItem: OperationTask): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: OperationTask, newItem: OperationTask): Boolean {
            return oldItem == newItem
        }
    }
}