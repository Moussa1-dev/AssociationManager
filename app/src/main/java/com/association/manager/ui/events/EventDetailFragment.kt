package com.association.manager.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.association.manager.R
import com.association.manager.util.toFormattedDateTime
import com.google.android.material.button.MaterialButton

class EventDetailFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = arguments?.getLong("eventId") ?: return

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvType = view.findViewById<TextView>(R.id.tv_type)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description)
        val tvLocation = view.findViewById<TextView>(R.id.tv_location)
        val tvDates = view.findViewById<TextView>(R.id.tv_dates)
        val btnEdit = view.findViewById<MaterialButton>(R.id.btn_edit)
        val btnDelete = view.findViewById<MaterialButton>(R.id.btn_delete)

        viewModel.getEventById(eventId).observe(viewLifecycleOwner) { event ->
            event?.let {
                tvTitle.text = it.title
                tvType.text = it.type.displayName()
                tvStatus.text = it.status.displayName()
                tvDescription.text = it.description
                tvLocation.text = "Lieu: ${it.location}"
                tvDates.text = "Du ${it.startDate.toFormattedDateTime()} au ${it.endDate.toFormattedDateTime()}"

                btnEdit.setOnClickListener {
                    val bundle = Bundle().apply { putLong("eventId", eventId) }
                    findNavController().navigate(R.id.addEventFragment, bundle)
                }

                btnDelete.setOnClickListener {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.confirm_delete)
                        .setMessage(R.string.confirm_delete_message)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            viewModel.delete(event)
                            findNavController().popBackStack()
                        }
                        .setNegativeButton(R.string.no, null)
                        .show()
                }
            }
        }
    }
}
