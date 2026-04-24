package com.association.manager.ui.auth

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.Member
import com.association.manager.data.model.MemberRole
import com.association.manager.util.SessionManager
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as AssociationApp).memberRepository
    private val sessionManager = SessionManager(application)

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                _loginResult.value = LoginResult.Error("Veuillez remplir tous les champs")
                return@launch
            }
            val member = repository.login(email, password)
            if (member != null) {
                sessionManager.saveLogin(member.id)
                _loginResult.value = LoginResult.Success(member)
            } else {
                _loginResult.value = LoginResult.Error("Email ou mot de passe incorrect")
            }
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            when {
                firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() -> {
                    _registerResult.value = RegisterResult.Error("Veuillez remplir tous les champs obligatoires")
                    return@launch
                }
                password != confirmPassword -> {
                    _registerResult.value = RegisterResult.Error("Les mots de passe ne correspondent pas")
                    return@launch
                }
                password.length < 6 -> {
                    _registerResult.value = RegisterResult.Error("Le mot de passe doit contenir au moins 6 caractères")
                    return@launch
                }
                else -> {
                    val existingMember = repository.getMemberByEmail(email)
                    if (existingMember != null) {
                        _registerResult.value = RegisterResult.Error("Un compte avec cet email existe déjà")
                        return@launch
                    }

                    val memberCount = repository.allMembers.value?.size ?: 0
                    val role = if (memberCount == 0) MemberRole.PRESIDENT else MemberRole.MEMBER

                    val member = Member(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phone = phone,
                        password = password,
                        role = role
                    )
                    val id = repository.insert(member)
                    sessionManager.saveLogin(id)
                    _registerResult.value = RegisterResult.Success(member.copy(id = id))
                }
            }
        }
    }

    fun isLoggedIn(): Boolean = sessionManager.isLoggedIn()

    fun logout() = sessionManager.logout()

    fun getLoggedInMemberId(): Long = sessionManager.getLoggedInMemberId()
}

sealed class LoginResult {
    data class Success(val member: Member) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

sealed class RegisterResult {
    data class Success(val member: Member) : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}
