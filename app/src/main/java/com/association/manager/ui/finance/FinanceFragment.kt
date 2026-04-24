package com.association.manager.ui.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.ui.adapters.TransactionAdapter
import com.association.manager.util.toFormattedCurrency
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class FinanceFragment : Fragment() {

    private val viewModel: FinanceViewModel by viewModels()
    private var currentTab = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvIncome = view.findViewById<TextView>(R.id.tv_income)
        val tvExpenses = view.findViewById<TextView>(R.id.tv_expenses)
        val tvBalance = view.findViewById<TextView>(R.id.tv_balance)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val rvTransactions = view.findViewById<RecyclerView>(R.id.rv_transactions)
        val tvNoData = view.findViewById<TextView>(R.id.tv_no_data)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add)

        val transactionAdapter = TransactionAdapter()
        rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        rvTransactions.adapter = transactionAdapter

        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            tvIncome.text = (income ?: 0.0).toFormattedCurrency()
        }

        viewModel.totalExpenses.observe(viewLifecycleOwner) { expenses ->
            tvExpenses.text = (expenses ?: 0.0).toFormattedCurrency()
        }

        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            tvBalance.text = (balance ?: 0.0).toFormattedCurrency()
        }

        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            if (currentTab == 0) {
                transactionAdapter.submitList(transactions)
                tvNoData.visibility = if (transactions.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab?.position ?: 0
                when (currentTab) {
                    0 -> {
                        tvNoData.text = getString(R.string.no_transactions)
                        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
                            transactionAdapter.submitList(transactions)
                            tvNoData.visibility = if (transactions.isNullOrEmpty()) View.VISIBLE else View.GONE
                        }
                    }
                    1 -> {
                        findNavController().navigate(R.id.action_finance_to_cotisation)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_finance_to_add_transaction)
        }
    }
}
