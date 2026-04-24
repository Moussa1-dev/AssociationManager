package com.association.manager.ui.members

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
import com.association.manager.data.model.Member
import com.association.manager.data.model.MemberRole
import com.association.manager.data.model.MemberStatus
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddMemberFragment : Fragment() {

    private val viewModel: MemberViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFirstName = view.findViewById<TextInputEditText>(R.id.et_first_name)
        val etLastName = view.findViewById<TextInputEditText>(R.id.et_last_name)
        val etEmail = view.findViewById<TextInputEditText>(R.id.et_email)
        val etPhone = view.findViewById<TextInputEditText>(R.id.et_phone)
        val etAddress = view.findViewById<TextInputEditText>(R.id.et_address)
        val etNotes = view.findViewById<TextInputEditText>(R.id.et_notes)
        val spinnerRole = view.findViewById<Spinner>(R.id.spinner_role)
        val spinnerStatus = view.findViewById<Spinner>(R.id.spinner_status)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_save)

        val roles = MemberRole.entries.map { it.displayName() }
        spinnerRole.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, roles)

        val statuses = MemberStatus.entries.map { it.displayName() }
        spinnerStatus.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses)

        val memberId = arguments?.getLong("memberId", -1L) ?: -1L
        if (memberId != -1L) {
            viewModel.getMemberById(memberId).observe(viewLifecycleOwner) { member ->
                member?.let {
                    etFirstName.setText(it.firstName)
                    etLastName.setText(it.lastName)
                    etEmail.setText(it.email)
                    etPhone.setText(it.phone)
                    etAddress.setText(it.address)
                    etNotes.setText(it.notes)
                    spinnerRole.setSelection(it.role.ordinal)
                    spinnerStatus.setSelection(it.status.ordinal)
                }
            }
        }

        btnSave.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                Toast.makeText(requireContext(), "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val member = Member(
                id = if (memberId != -1L) memberId else 0,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = etPhone.text.toString().trim(),
                address = etAddress.text.toString().trim(),
                role = MemberRole.entries[spinnerRole.selectedItemPosition],
                status = MemberStatus.entries[spinnerStatus.selectedItemPosition],
                notes = etNotes.text.toString().trim()
            )

            if (memberId != -1L) {
                viewModel.update(member)
            } else {
                viewModel.insert(member)
            }
            findNavController().popBackStack()
        }
    }
}
