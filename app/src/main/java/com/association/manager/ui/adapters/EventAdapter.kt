package com.association.manager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Event
import com.association.manager.util.toFormattedDateTime

class EventAdapter(
    private val onClick: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvType: TextView = itemView.findViewById(R.id.tv_type)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvLocation: TextView = itemView.findViewById(R.id.tv_location)

        fun bind(event: Event) {
            tvTitle.text = event.title
            tvType.text = event.type.displayName()
            tvDate.text = event.startDate.toFormattedDateTime()
            tvLocation.text = event.location
            tvLocation.visibility = if (event.location.isBlank()) View.GONE else View.VISIBLE
            itemView.setOnClickListener { onClick(event) }
        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }
}
