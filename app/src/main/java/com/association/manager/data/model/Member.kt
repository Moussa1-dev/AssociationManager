package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val address: String = "",
    val role: MemberRole = MemberRole.MEMBER,
    val status: MemberStatus = MemberStatus.ACTIVE,
    val joinDate: Long = System.currentTimeMillis(),
    val birthDate: Long? = null,
    val profileImageUri: String? = null,
    val notes: String = "",
    val password: String = ""
)

enum class MemberRole {
    PRESIDENT,
    VICE_PRESIDENT,
    SECRETARY,
    TREASURER,
    MEMBER;

    fun displayName(): String = when (this) {
        PRESIDENT -> "Président"
        VICE_PRESIDENT -> "Vice-Président"
        SECRETARY -> "Secrétaire"
        TREASURER -> "Trésorier"
        MEMBER -> "Membre"
    }
}

enum class MemberStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED;

    fun displayName(): String = when (this) {
        ACTIVE -> "Actif"
        INACTIVE -> "Inactif"
        SUSPENDED -> "Suspendu"
    }
}
