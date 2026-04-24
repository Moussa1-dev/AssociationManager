package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val priority: AnnouncementPriority = AnnouncementPriority.NORMAL,
    val authorId: Long? = null,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long? = null
)

enum class AnnouncementPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT;

    fun displayName(): String = when (this) {
        LOW -> "Basse"
        NORMAL -> "Normale"
        HIGH -> "Haute"
        URGENT -> "Urgente"
    }
}
