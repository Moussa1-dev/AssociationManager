package com.association.manager.ui.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.association.manager.R
import com.association.manager.data.model.Event
import com.association.manager.data.model.EventType
import com.association.manager.util.toFormattedDateTime
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class AddEventFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels()
    private var startDate: Long = System.currentTimeMillis()
    private var endDate: Long = System.currentTimeMillis() + 3600000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<TextInputEditText>(R.id.et_title)
        val etDescription = view.findViewById<TextInputEditText>(R.id.et_description)
        val etLocation = view.findViewById<TextInputEditText>(R.id.et_location)
        val btnStartDate = view.findViewById<MaterialButton>(R.id.btn_start_date)
        val btnEndDate = view.findViewById<MaterialButton>(R.id.btn_end_date)
        val spinnerType = view.findViewById<Spinner>(R.id.spinner_type)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_save)

        val types = EventType.entries.map { it.displayName() }
        spinnerType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)

        btnStartDate.text = "Début: ${startDate.toFormattedDateTime()}"
        btnEndDate.text = "Fin: ${endDate.toFormattedDateTime()}"

        btnStartDate.setOnClickListener {
            showDateTimePicker { date ->
                startDate = date
                btnStartDate.text = "Début: ${startDate.toFormattedDateTime()}"
            }
        }

        btnEndDate.setOnClickListener {
            showDateTimePicker { date ->
                endDate = date
                btnEndDate.text = "Fin: ${endDate.toFormattedDateTime()}"
            }
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Le titre est obligatoire", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val event = Event(
                title = title,
                description = etDescription.text.toString().trim(),
                location = etLocation.text.toString().trim(),
                startDate = startDate,
                endDate = endDate,
                type = EventType.entries[spinnerType.selectedItemPosition]
            )

            viewModel.insert(event)
            findNavController().popBackStack()
        }
    }

    private fun showDateTimePicker(callback: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, day ->
            TimePickerDialog(requireContext(), { _, hour, minute ->
                calendar.set(year, month, day, hour, minute)
                callback(calendar.timeInMillis)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}
