package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "votes")
data class Vote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val options: String = "",
    val status: VoteStatus = VoteStatus.OPEN,
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long,
    val createdBy: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "vote_responses")
data class VoteResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val voteId: Long,
    val memberId: Long,
    val selectedOption: String,
    val votedAt: Long = System.currentTimeMillis()
)

enum class VoteStatus {
    OPEN,
    CLOSED,
    CANCELLED;

    fun displayName(): String = when (this) {
        OPEN -> "Ouvert"
        CLOSED -> "Clôturé"
        CANCELLED -> "Annulé"
    }
}
