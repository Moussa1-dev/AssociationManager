package com.association.manager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Vote
import com.association.manager.util.toFormattedDate

class VoteAdapter(
    private val onClick: (Vote) -> Unit
) : ListAdapter<Vote, VoteAdapter.VoteViewHolder>(VoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vote, parent, false)
        return VoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(vote: Vote) {
            tvTitle.text = vote.title
            tvStatus.text = vote.status.displayName()
            tvDescription.text = vote.description
            tvDate.text = "Fin: ${vote.endDate.toFormattedDate()}"
            itemView.setOnClickListener { onClick(vote) }
        }
    }

    class VoteDiffCallback : DiffUtil.ItemCallback<Vote>() {
        override fun areItemsTheSame(oldItem: Vote, newItem: Vote) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Vote, newItem: Vote) = oldItem == newItem
    }
}
