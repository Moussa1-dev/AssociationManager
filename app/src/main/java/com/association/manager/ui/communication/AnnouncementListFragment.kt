package com.association.manager.ui.communication

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
import com.association.manager.ui.adapters.AnnouncementAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AnnouncementListFragment : Fragment() {

    private val viewModel: AnnouncementViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_announcement_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAnnouncements = view.findViewById<RecyclerView>(R.id.rv_announcements)
        val tvNoAnnouncements = view.findViewById<TextView>(R.id.tv_no_announcements)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add_announcement)

        val adapter = AnnouncementAdapter { announcement ->
            viewModel.markAsRead(announcement.id)
        }
        rvAnnouncements.layoutManager = LinearLayoutManager(requireContext())
        rvAnnouncements.adapter = adapter

        viewModel.allAnnouncements.observe(viewLifecycleOwner) { announcements ->
            adapter.submitList(announcements)
            tvNoAnnouncements.visibility = if (announcements.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_announcements_to_add)
        }
    }
}
