package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.VoteDao
import com.association.manager.data.model.Vote
import com.association.manager.data.model.VoteResponse
import com.association.manager.data.model.VoteStatus

class VoteRepository(private val voteDao: VoteDao) {

    val allVotes: LiveData<List<Vote>> = voteDao.getAllVotes()
    val activeVoteCount: LiveData<Int> = voteDao.getActiveVoteCount()

    suspend fun insertVote(vote: Vote): Long = voteDao.insertVote(vote)

    suspend fun updateVote(vote: Vote) = voteDao.updateVote(vote)

    suspend fun deleteVote(vote: Vote) = voteDao.deleteVote(vote)

    fun getVoteById(id: Long): LiveData<Vote?> = voteDao.getVoteById(id)

    fun getVotesByStatus(status: VoteStatus): LiveData<List<Vote>> =
        voteDao.getVotesByStatus(status)

    suspend fun insertResponse(response: VoteResponse): Long = voteDao.insertResponse(response)

    fun getResponsesByVoteId(voteId: Long): LiveData<List<VoteResponse>> =
        voteDao.getResponsesByVoteId(voteId)

    fun getResponseCount(voteId: Long): LiveData<Int> = voteDao.getResponseCount(voteId)

    suspend fun getMemberVoteResponse(voteId: Long, memberId: Long): VoteResponse? =
        voteDao.getMemberVoteResponse(voteId, memberId)
}
