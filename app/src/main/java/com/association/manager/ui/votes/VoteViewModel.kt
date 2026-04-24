package com.association.manager.ui.votes

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.Vote
import com.association.manager.data.model.VoteResponse
import com.association.manager.data.model.VoteStatus
import kotlinx.coroutines.launch

class VoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as AssociationApp).voteRepository

    val allVotes: LiveData<List<Vote>> = repository.allVotes
    val activeVoteCount: LiveData<Int> = repository.activeVoteCount

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    fun insertVote(vote: Vote) = viewModelScope.launch {
        repository.insertVote(vote)
        _operationResult.value = "Vote créé"
    }

    fun updateVote(vote: Vote) = viewModelScope.launch {
        repository.updateVote(vote)
        _operationResult.value = "Vote mis à jour"
    }

    fun deleteVote(vote: Vote) = viewModelScope.launch {
        repository.deleteVote(vote)
        _operationResult.value = "Vote supprimé"
    }

    fun submitResponse(voteId: Long, memberId: Long, option: String) = viewModelScope.launch {
        val existingResponse = repository.getMemberVoteResponse(voteId, memberId)
        if (existingResponse != null) {
            _operationResult.value = "Vous avez déjà voté"
            return@launch
        }
        val response = VoteResponse(
            voteId = voteId,
            memberId = memberId,
            selectedOption = option
        )
        repository.insertResponse(response)
        _operationResult.value = "Vote enregistré"
    }

    fun getVoteById(id: Long): LiveData<Vote?> = repository.getVoteById(id)

    fun getResponsesByVoteId(voteId: Long): LiveData<List<VoteResponse>> =
        repository.getResponsesByVoteId(voteId)

    fun getResponseCount(voteId: Long): LiveData<Int> = repository.getResponseCount(voteId)

    fun getVotesByStatus(status: VoteStatus): LiveData<List<Vote>> =
        repository.getVotesByStatus(status)

    fun clearOperationResult() {
        _operationResult.value = null
    }
}
