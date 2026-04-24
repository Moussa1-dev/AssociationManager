package com.association.manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.association.manager.data.model.Vote
import com.association.manager.data.model.VoteResponse
import com.association.manager.data.model.VoteStatus

@Dao
interface VoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVote(vote: Vote): Long

    @Update
    suspend fun updateVote(vote: Vote)

    @Delete
    suspend fun deleteVote(vote: Vote)

    @Query("SELECT * FROM votes ORDER BY createdAt DESC")
    fun getAllVotes(): LiveData<List<Vote>>

    @Query("SELECT * FROM votes WHERE id = :id")
    fun getVoteById(id: Long): LiveData<Vote?>

    @Query("SELECT * FROM votes WHERE status = :status ORDER BY createdAt DESC")
    fun getVotesByStatus(status: VoteStatus): LiveData<List<Vote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(response: VoteResponse): Long

    @Query("SELECT * FROM vote_responses WHERE voteId = :voteId")
    fun getResponsesByVoteId(voteId: Long): LiveData<List<VoteResponse>>

    @Query("SELECT COUNT(*) FROM vote_responses WHERE voteId = :voteId")
    fun getResponseCount(voteId: Long): LiveData<Int>

    @Query("SELECT * FROM vote_responses WHERE voteId = :voteId AND memberId = :memberId LIMIT 1")
    suspend fun getMemberVoteResponse(voteId: Long, memberId: Long): VoteResponse?

    @Query("SELECT COUNT(*) FROM votes WHERE status = 'OPEN'")
    fun getActiveVoteCount(): LiveData<Int>
}
