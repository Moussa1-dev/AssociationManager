package com.association.manager.ui.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.data.model.Cotisation
import com.association.manager.data.model.CotisationStatus
import com.association.manager.ui.adapters.TransactionAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CotisationFragment : Fragment() {

    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cotisation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvNoCotisations = view.findViewById<TextView>(R.id.tv_no_cotisations)
        val rvCotisations = view.findViewById<RecyclerView>(R.id.rv_cotisations)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add_cotisation)

        rvCotisations.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allCotisations.observe(viewLifecycleOwner) { cotisations ->
            tvNoCotisations.visibility = if (cotisations.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        fabAdd.setOnClickListener {
            Toast.makeText(requireContext(), "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }
    }
}
