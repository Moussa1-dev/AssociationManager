package com.association.manager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Transaction
import com.association.manager.data.model.TransactionType
import com.association.manager.util.toFormattedCurrency
import com.association.manager.util.toFormattedDate

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val tvCategory: TextView = itemView.findViewById(R.id.tv_category)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvAmount: TextView = itemView.findViewById(R.id.tv_amount)

        fun bind(transaction: Transaction) {
            tvDescription.text = transaction.description
            tvCategory.text = transaction.category.displayName()
            tvDate.text = transaction.date.toFormattedDate()

            val prefix = if (transaction.type == TransactionType.INCOME) "+" else "-"
            tvAmount.text = "$prefix${transaction.amount.toFormattedCurrency()}"
            tvAmount.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (transaction.type == TransactionType.INCOME) R.color.income_green else R.color.expense_red
                )
            )
        }
    }

    class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
    }
}
