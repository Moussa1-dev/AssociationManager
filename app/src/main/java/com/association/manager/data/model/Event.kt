package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val location: String = "",
    val startDate: Long,
    val endDate: Long,
    val type: EventType = EventType.MEETING,
    val status: EventStatus = EventStatus.PLANNED,
    val organizerId: Long? = null,
    val maxParticipants: Int? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class EventType {
    MEETING,
    ASSEMBLY,
    ACTIVITY,
    TRAINING,
    SOCIAL,
    OTHER;

    fun displayName(): String = when (this) {
        MEETING -> "Réunion"
        ASSEMBLY -> "Assemblée Générale"
        ACTIVITY -> "Activité"
        TRAINING -> "Formation"
        SOCIAL -> "Événement Social"
        OTHER -> "Autre"
    }
}

enum class EventStatus {
    PLANNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    fun displayName(): String = when (this) {
        PLANNED -> "Planifié"
        IN_PROGRESS -> "En cours"
        COMPLETED -> "Terminé"
        CANCELLED -> "Annulé"
    }
}
