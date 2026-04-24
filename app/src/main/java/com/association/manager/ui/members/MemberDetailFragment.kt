package com.association.manager.ui.members

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
import com.association.manager.util.toFormattedDate
import com.google.android.material.button.MaterialButton

class MemberDetailFragment : Fragment() {

    private val viewModel: MemberViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_member_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val memberId = arguments?.getLong("memberId") ?: return

        val tvName = view.findViewById<TextView>(R.id.tv_member_name)
        val tvRole = view.findViewById<TextView>(R.id.tv_member_role)
        val tvStatus = view.findViewById<TextView>(R.id.tv_member_status)
        val tvEmail = view.findViewById<TextView>(R.id.tv_email)
        val tvPhone = view.findViewById<TextView>(R.id.tv_phone)
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)
        val tvJoinDate = view.findViewById<TextView>(R.id.tv_join_date)
        val tvNotes = view.findViewById<TextView>(R.id.tv_notes)
        val btnEdit = view.findViewById<MaterialButton>(R.id.btn_edit)
        val btnDelete = view.findViewById<MaterialButton>(R.id.btn_delete)

        viewModel.getMemberById(memberId).observe(viewLifecycleOwner) { member ->
            member?.let {
                tvName.text = "${it.firstName} ${it.lastName}"
                tvRole.text = it.role.displayName()
                tvStatus.text = it.status.displayName()
                tvEmail.text = "Email: ${it.email}"
                tvPhone.text = "Tél: ${it.phone}"
                tvAddress.text = "Adresse: ${it.address}"
                tvJoinDate.text = "Membre depuis: ${it.joinDate.toFormattedDate()}"
                if (it.notes.isNotBlank()) {
                    tvNotes.visibility = View.VISIBLE
                    tvNotes.text = "Notes: ${it.notes}"
                }

                btnEdit.setOnClickListener {
                    val bundle = Bundle().apply { putLong("memberId", memberId) }
                    findNavController().navigate(R.id.addMemberFragment, bundle)
                }

                btnDelete.setOnClickListener {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.confirm_delete)
                        .setMessage(R.string.confirm_delete_message)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            viewModel.delete(member)
                            findNavController().popBackStack()
                        }
                        .setNegativeButton(R.string.no, null)
                        .show()
                }
            }
        }
    }
}
