package com.association.manager.ui.votes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.association.manager.R
import com.association.manager.data.model.VoteStatus
import com.association.manager.ui.auth.AuthViewModel
import com.association.manager.util.toFormattedDate
import com.google.android.material.button.MaterialButton

class VoteDetailFragment : Fragment() {

    private val voteViewModel: VoteViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vote_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val voteId = arguments?.getLong("voteId") ?: return

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description)
        val tvDates = view.findViewById<TextView>(R.id.tv_dates)
        val rgOptions = view.findViewById<RadioGroup>(R.id.rg_options)
        val btnVote = view.findViewById<MaterialButton>(R.id.btn_vote)

        voteViewModel.getVoteById(voteId).observe(viewLifecycleOwner) { vote ->
            vote?.let {
                tvTitle.text = it.title
                tvStatus.text = it.status.displayName()
                tvDescription.text = it.description
                tvDates.text = "Début: ${it.startDate.toFormattedDate()} - Fin: ${it.endDate.toFormattedDate()}"

                rgOptions.removeAllViews()
                val options = it.options.split("\n").filter { opt -> opt.isNotBlank() }
                options.forEachIndexed { index, option ->
                    val radioButton = RadioButton(requireContext()).apply {
                        id = View.generateViewId()
                        text = option.trim()
                        textSize = 16f
                    }
                    rgOptions.addView(radioButton)
                }

                btnVote.isEnabled = it.status == VoteStatus.OPEN

                btnVote.setOnClickListener {
                    val selectedId = rgOptions.checkedRadioButtonId
                    if (selectedId == -1) {
                        Toast.makeText(requireContext(), "Veuillez sélectionner une option", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val selectedOption = view.findViewById<RadioButton>(selectedId).text.toString()
                    val memberId = authViewModel.getLoggedInMemberId()
                    voteViewModel.submitResponse(voteId, memberId, selectedOption)
                }
            }
        }

        voteViewModel.operationResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                voteViewModel.clearOperationResult()
            }
        }
    }
}
