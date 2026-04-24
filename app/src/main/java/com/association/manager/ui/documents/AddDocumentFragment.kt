package com.association.manager.ui.documents

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
import com.association.manager.data.model.Document
import com.association.manager.data.model.DocumentType
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddDocumentFragment : Fragment() {

    private val viewModel: DocumentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<TextInputEditText>(R.id.et_title)
        val etDescription = view.findViewById<TextInputEditText>(R.id.et_description)
        val spinnerType = view.findViewById<Spinner>(R.id.spinner_type)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_save)

        val types = DocumentType.entries.map { it.displayName() }
        spinnerType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Le titre est obligatoire", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val document = Document(
                title = title,
                description = etDescription.text.toString().trim(),
                type = DocumentType.entries[spinnerType.selectedItemPosition]
            )

            viewModel.insert(document)
            findNavController().popBackStack()
        }
    }
}
