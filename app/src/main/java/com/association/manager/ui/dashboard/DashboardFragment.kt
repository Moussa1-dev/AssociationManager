package com.association.manager.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.association.manager.R
import com.association.manager.ui.adapters.EventAdapter
import com.association.manager.ui.auth.AuthViewModel
import com.association.manager.util.toFormattedCurrency

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvMemberCount = view.findViewById<TextView>(R.id.tv_member_count)
        val tvBalance = view.findViewById<TextView>(R.id.tv_balance)
        val tvEventCount = view.findViewById<TextView>(R.id.tv_event_count)
        val tvVoteCount = view.findViewById<TextView>(R.id.tv_vote_count)
        val rvUpcomingEvents = view.findViewById<RecyclerView>(R.id.rv_upcoming_events)
        val tvNoEvents = view.findViewById<TextView>(R.id.tv_no_upcoming_events)
        val btnLogout = view.findViewById<ImageButton>(R.id.btn_logout)

        val eventAdapter = EventAdapter { event ->
            val bundle = Bundle().apply { putLong("eventId", event.id) }
            findNavController().navigate(R.id.eventDetailFragment, bundle)
        }
        rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext())
        rvUpcomingEvents.adapter = eventAdapter

        dashboardViewModel.memberCount.observe(viewLifecycleOwner) { count ->
            tvMemberCount.text = (count ?: 0).toString()
        }

        dashboardViewModel.balance.observe(viewLifecycleOwner) { balance ->
            tvBalance.text = (balance ?: 0.0).toFormattedCurrency()
        }

        dashboardViewModel.eventCount.observe(viewLifecycleOwner) { count ->
            tvEventCount.text = (count ?: 0).toString()
        }

        dashboardViewModel.activeVotes.observe(viewLifecycleOwner) { count ->
            tvVoteCount.text = (count ?: 0).toString()
        }

        dashboardViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            val upcoming = events?.take(5) ?: emptyList()
            eventAdapter.submitList(upcoming)
            tvNoEvents.visibility = if (upcoming.isEmpty()) View.VISIBLE else View.GONE
            rvUpcomingEvents.visibility = if (upcoming.isEmpty()) View.GONE else View.VISIBLE
        }

        btnLogout.setOnClickListener {
            authViewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }
    }
}
