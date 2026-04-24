package com.association.manager.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.association.manager.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFirstName = view.findViewById<TextInputEditText>(R.id.et_first_name)
        val etLastName = view.findViewById<TextInputEditText>(R.id.et_last_name)
        val etEmail = view.findViewById<TextInputEditText>(R.id.et_email)
        val etPhone = view.findViewById<TextInputEditText>(R.id.et_phone)
        val etPassword = view.findViewById<TextInputEditText>(R.id.et_password)
        val etConfirmPassword = view.findViewById<TextInputEditText>(R.id.et_confirm_password)
        val btnRegister = view.findViewById<MaterialButton>(R.id.btn_register)
        val tvGoLogin = view.findViewById<View>(R.id.tv_go_login)

        btnRegister.setOnClickListener {
            viewModel.register(
                firstName = etFirstName.text.toString().trim(),
                lastName = etLastName.text.toString().trim(),
                email = etEmail.text.toString().trim(),
                phone = etPhone.text.toString().trim(),
                password = etPassword.text.toString(),
                confirmPassword = etConfirmPassword.text.toString()
            )
        }

        tvGoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RegisterResult.Success -> {
                    Toast.makeText(requireContext(), "Inscription réussie!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_register_to_dashboard)
                }
                is RegisterResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
