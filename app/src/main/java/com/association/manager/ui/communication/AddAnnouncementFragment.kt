package com.association.manager.ui.communication

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
import com.association.manager.data.model.Announcement
import com.association.manager.data.model.AnnouncementPriority
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddAnnouncementFragment : Fragment() {

    private val viewModel: AnnouncementViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<TextInputEditText>(R.id.et_title)
        val etContent = view.findViewById<TextInputEditText>(R.id.et_content)
        val spinnerPriority = view.findViewById<Spinner>(R.id.spinner_priority)
        val btnPublish = view.findViewById<MaterialButton>(R.id.btn_publish)

        val priorities = AnnouncementPriority.entries.map { it.displayName() }
        spinnerPriority.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, priorities)
        spinnerPriority.setSelection(1)

        btnPublish.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etContent.text.toString().trim()

            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val announcement = Announcement(
                title = title,
                content = content,
                priority = AnnouncementPriority.entries[spinnerPriority.selectedItemPosition]
            )

            viewModel.insert(announcement)
            findNavController().popBackStack()
        }
    }
}
