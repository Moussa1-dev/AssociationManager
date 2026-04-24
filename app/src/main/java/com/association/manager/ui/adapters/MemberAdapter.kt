package com.association.manager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Member

class MemberAdapter(
    private val onClick: (Member) -> Unit
) : ListAdapter<Member, MemberAdapter.MemberViewHolder>(MemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvRole: TextView = itemView.findViewById(R.id.tv_role)
        private val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)

        fun bind(member: Member) {
            tvName.text = "${member.firstName} ${member.lastName}"
            tvRole.text = member.role.displayName()
            tvEmail.text = member.email
            tvStatus.text = member.status.displayName()
            itemView.setOnClickListener { onClick(member) }
        }
    }

    class MemberDiffCallback : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Member, newItem: Member) = oldItem == newItem
    }
}
