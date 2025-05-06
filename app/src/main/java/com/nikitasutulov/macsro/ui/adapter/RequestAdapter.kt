package com.nikitasutulov.macsro.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikitasutulov.macsro.data.ui.Request
import com.nikitasutulov.macsro.databinding.RequestCardBinding

class RequestAdapter(
    private val onMarkAsReadButtonClick: ((Request) -> Unit)? = null
) : ListAdapter<Request, RequestAdapter.RequestViewHolder>(RequestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = RequestCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = getItem(position)
        holder.bind(request, onMarkAsReadButtonClick)
    }

    class RequestViewHolder(private val binding: RequestCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(request: Request, onMarkAsReadButtonClick: ((Request) -> Unit)?) {
            binding.requestSenderTextView.text = request.from
            binding.requestReceiverTextView.text = request.to
            binding.requestTextTextView.text = request.text
            if (request.isRead) {
                binding.isReadTextView.visibility = View.VISIBLE
            } else {
                binding.isReadTextView.visibility = View.INVISIBLE
            }
            if (request.canBeRead) {
                binding.markAsReadButton.visibility = View.VISIBLE
                binding.markAsReadButton.setOnClickListener {
                    onMarkAsReadButtonClick?.invoke(
                        request
                    )
                }
            } else {
                binding.markAsReadButton.visibility = View.GONE
            }
        }
    }

    class RequestDiffCallback : DiffUtil.ItemCallback<Request>() {
        override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem.gid == newItem.gid
        }

        override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem == newItem
        }
    }
}
