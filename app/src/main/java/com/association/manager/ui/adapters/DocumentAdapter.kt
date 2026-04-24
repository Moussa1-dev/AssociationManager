package com.association.manager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Document
import com.association.manager.util.toFormattedDate

class DocumentAdapter(
    private val onClick: (Document) -> Unit = {}
) : ListAdapter<Document, DocumentAdapter.DocumentViewHolder>(DocumentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_document, parent, false)
        return DocumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvType: TextView = itemView.findViewById(R.id.tv_type)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(document: Document) {
            tvTitle.text = document.title
            tvType.text = document.type.displayName()
            tvDate.text = document.createdAt.toFormattedDate()
            itemView.setOnClickListener { onClick(document) }
        }
    }

    class DocumentDiffCallback : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Document, newItem: Document) = oldItem == newItem
    }
}
