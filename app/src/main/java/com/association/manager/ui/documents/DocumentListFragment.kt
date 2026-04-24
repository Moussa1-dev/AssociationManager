package com.association.manager.ui.documents

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
import com.association.manager.ui.adapters.DocumentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DocumentListFragment : Fragment() {

    private val viewModel: DocumentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_document_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvDocuments = view.findViewById<RecyclerView>(R.id.rv_documents)
        val tvNoDocuments = view.findViewById<TextView>(R.id.tv_no_documents)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add_document)

        val adapter = DocumentAdapter()
        rvDocuments.layoutManager = LinearLayoutManager(requireContext())
        rvDocuments.adapter = adapter

        viewModel.allDocuments.observe(viewLifecycleOwner) { documents ->
            adapter.submitList(documents)
            tvNoDocuments.visibility = if (documents.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_documents_to_add)
        }
    }
}
