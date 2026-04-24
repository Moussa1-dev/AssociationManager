package com.association.manager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.association.manager.R

class MoreFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.card_announcements).setOnClickListener {
            findNavController().navigate(R.id.action_more_to_announcements)
        }

        view.findViewById<CardView>(R.id.card_documents).setOnClickListener {
            findNavController().navigate(R.id.action_more_to_documents)
        }

        view.findViewById<CardView>(R.id.card_votes).setOnClickListener {
            findNavController().navigate(R.id.action_more_to_votes)
        }
    }
}
