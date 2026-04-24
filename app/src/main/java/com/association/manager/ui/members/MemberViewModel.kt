package com.association.manager.ui.members

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.Member
import com.association.manager.data.model.MemberRole
import com.association.manager.data.model.MemberStatus
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as AssociationApp).memberRepository

    val allMembers: LiveData<List<Member>> = repository.allMembers
    val memberCount: LiveData<Int> = repository.memberCount

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    fun insert(member: Member) = viewModelScope.launch {
        repository.insert(member)
        _operationResult.value = "Membre ajouté avec succès"
    }

    fun update(member: Member) = viewModelScope.launch {
        repository.update(member)
        _operationResult.value = "Membre mis à jour avec succès"
    }

    fun delete(member: Member) = viewModelScope.launch {
        repository.delete(member)
        _operationResult.value = "Membre supprimé"
    }

    fun getMemberById(id: Long): LiveData<Member?> = repository.getMemberById(id)

    fun getMembersByStatus(status: MemberStatus): LiveData<List<Member>> =
        repository.getMembersByStatus(status)

    fun getMembersByRole(role: MemberRole): LiveData<List<Member>> =
        repository.getMembersByRole(role)

    fun searchMembers(query: String): LiveData<List<Member>> =
        repository.searchMembers(query)

    fun clearOperationResult() {
        _operationResult.value = null
    }
}
