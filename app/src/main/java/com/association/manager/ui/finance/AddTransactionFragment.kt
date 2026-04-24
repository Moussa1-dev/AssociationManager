package com.association.manager.ui.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.association.manager.R
import com.association.manager.data.model.Transaction
import com.association.manager.data.model.TransactionCategory
import com.association.manager.data.model.TransactionType
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddTransactionFragment : Fragment() {

    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etDescription = view.findViewById<TextInputEditText>(R.id.et_description)
        val etAmount = view.findViewById<TextInputEditText>(R.id.et_amount)
        val rgType = view.findViewById<RadioGroup>(R.id.rg_type)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinner_category)
        val etNotes = view.findViewById<TextInputEditText>(R.id.et_notes)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_save)

        val categories = TransactionCategory.entries.map { it.displayName() }
        spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)

        rgType.check(R.id.rb_income)

        btnSave.setOnClickListener {
            val description = etDescription.text.toString().trim()
            val amountStr = etAmount.text.toString().trim()

            if (description.isBlank() || amountStr.isBlank()) {
                Toast.makeText(requireContext(), "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(requireContext(), "Montant invalide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = if (rgType.checkedRadioButtonId == R.id.rb_income) TransactionType.INCOME else TransactionType.EXPENSE

            val transaction = Transaction(
                description = description,
                amount = amount,
                type = type,
                category = TransactionCategory.entries[spinnerCategory.selectedItemPosition],
                notes = etNotes.text.toString().trim()
            )

            viewModel.insertTransaction(transaction)
            findNavController().popBackStack()
        }
    }
}
