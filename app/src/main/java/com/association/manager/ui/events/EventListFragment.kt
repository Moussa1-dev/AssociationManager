package com.association.manager.ui.events

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
import com.association.manager.ui.adapters.EventAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EventListFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvEvents = view.findViewById<RecyclerView>(R.id.rv_events)
        val tvNoEvents = view.findViewById<TextView>(R.id.tv_no_events)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fab_add_event)

        val adapter = EventAdapter { event ->
            val bundle = Bundle().apply { putLong("eventId", event.id) }
            findNavController().navigate(R.id.action_events_to_detail, bundle)
        }
        rvEvents.layoutManager = LinearLayoutManager(requireContext())
        rvEvents.adapter = adapter

        viewModel.allEvents.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
            tvNoEvents.visibility = if (events.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_events_to_add)
        }
    }
}
