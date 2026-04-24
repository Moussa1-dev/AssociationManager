package com.association.manager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Announcement
import com.association.manager.util.toFormattedDateTime

class AnnouncementAdapter(
    private val onClick: (Announcement) -> Unit = {}
) : ListAdapter<Announcement, AnnouncementAdapter.AnnouncementViewHolder>(AnnouncementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnnouncementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private val tvPriority: TextView = itemView.findViewById(R.id.tv_priority)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(announcement: Announcement) {
            tvTitle.text = announcement.title
            tvContent.text = announcement.content
            tvPriority.text = announcement.priority.displayName()
            tvDate.text = announcement.createdAt.toFormattedDateTime()
            itemView.setOnClickListener { onClick(announcement) }
        }
    }

    class AnnouncementDiffCallback : DiffUtil.ItemCallback<Announcement>() {
        override fun areItemsTheSame(oldItem: Announcement, newItem: Announcement) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Announcement, newItem: Announcement) = oldItem == newItem
    }
}
