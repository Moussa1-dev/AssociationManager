package com.association.manager.ui.members

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.association.manager.ui.adapters.MemberAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MemberListFragment : Fragment() {

    private val viewModel: MemberViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_member_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvMembers = view.findViewById<RecyclerView>(R.id.rv_members)
        val tvNoMembers = view.findViewById<TextView>(R.id.tv_no_members)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add_member)
        val etSearch = view.findViewById<TextInputEditText>(R.id.et_search)

        val adapter = MemberAdapter { member ->
            val bundle = Bundle().apply { putLong("memberId", member.id) }
            findNavController().navigate(R.id.action_members_to_detail, bundle)
        }
        rvMembers.layoutManager = LinearLayoutManager(requireContext())
        rvMembers.adapter = adapter

        viewModel.allMembers.observe(viewLifecycleOwner) { members ->
            adapter.submitList(members)
            tvNoMembers.visibility = if (members.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    viewModel.searchMembers(query).observe(viewLifecycleOwner) { members ->
                        adapter.submitList(members)
                    }
                } else {
                    viewModel.allMembers.observe(viewLifecycleOwner) { members ->
                        adapter.submitList(members)
                    }
                }
            }
        })

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_members_to_add)
        }
    }
}
