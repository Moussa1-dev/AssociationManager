package com.association.manager.ui.votes

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.association.manager.R
import com.association.manager.data.model.Vote
import com.association.manager.util.toFormattedDate
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class CreateVoteFragment : Fragment() {

    private val viewModel: VoteViewModel by viewModels()
    private var endDate: Long = System.currentTimeMillis() + 7 * 24 * 3600000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_vote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<TextInputEditText>(R.id.et_title)
        val etDescription = view.findViewById<TextInputEditText>(R.id.et_description)
        val etOptions = view.findViewById<TextInputEditText>(R.id.et_options)
        val btnEndDate = view.findViewById<MaterialButton>(R.id.btn_end_date)
        val btnCreate = view.findViewById<MaterialButton>(R.id.btn_create)

        btnEndDate.text = "Fin: ${endDate.toFormattedDate()}"

        btnEndDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                calendar.set(year, month, day)
                endDate = calendar.timeInMillis
                btnEndDate.text = "Fin: ${endDate.toFormattedDate()}"
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnCreate.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val options = etOptions.text.toString().trim()

            if (title.isBlank() || options.isBlank()) {
                Toast.makeText(requireContext(), "Titre et options sont obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val vote = Vote(
                title = title,
                description = etDescription.text.toString().trim(),
                options = options,
                endDate = endDate
            )

            viewModel.insertVote(vote)
            findNavController().popBackStack()
        }
    }
}
