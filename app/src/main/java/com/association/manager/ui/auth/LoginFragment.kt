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

class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isLoggedIn()) {
            findNavController().navigate(R.id.action_login_to_dashboard)
            return
        }

        val etEmail = view.findViewById<TextInputEditText>(R.id.et_email)
        val etPassword = view.findViewById<TextInputEditText>(R.id.et_password)
        val btnLogin = view.findViewById<MaterialButton>(R.id.btn_login)
        val tvGoRegister = view.findViewById<View>(R.id.tv_go_register)

        btnLogin.setOnClickListener {
            viewModel.login(
                etEmail.text.toString().trim(),
                etPassword.text.toString()
            )
        }

        tvGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginResult.Success -> {
                    findNavController().navigate(R.id.action_login_to_dashboard)
                }
                is LoginResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
