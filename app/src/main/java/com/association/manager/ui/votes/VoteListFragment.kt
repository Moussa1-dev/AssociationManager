package com.association.manager.ui.votes

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
import com.association.manager.ui.adapters.VoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VoteListFragment : Fragment() {

    private val viewModel: VoteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vote_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvVotes = view.findViewById<RecyclerView>(R.id.rv_votes)
        val tvNoVotes = view.findViewById<TextView>(R.id.tv_no_votes)
        val fabCreate = view.findViewById<FloatingActionButton>(R.id.fab_create_vote)

        val adapter = VoteAdapter { vote ->
            val bundle = Bundle().apply { putLong("voteId", vote.id) }
            findNavController().navigate(R.id.action_votes_to_detail, bundle)
        }
        rvVotes.layoutManager = LinearLayoutManager(requireContext())
        rvVotes.adapter = adapter

        viewModel.allVotes.observe(viewLifecycleOwner) { votes ->
            adapter.submitList(votes)
            tvNoVotes.visibility = if (votes.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        fabCreate.setOnClickListener {
            findNavController().navigate(R.id.action_votes_to_create)
        }
    }
}
