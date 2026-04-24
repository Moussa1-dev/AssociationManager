package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class Document(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val type: DocumentType = DocumentType.OTHER,
    val fileUri: String = "",
    val uploadedBy: Long? = null,
    val eventId: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class DocumentType {
    PV,
    REPORT,
    BUDGET,
    STATUTE,
    LETTER,
    PHOTO,
    OTHER;

    fun displayName(): String = when (this) {
        PV -> "Procès-verbal"
        REPORT -> "Rapport"
        BUDGET -> "Budget"
        STATUTE -> "Statut"
        LETTER -> "Courrier"
        PHOTO -> "Photo"
        OTHER -> "Autre"
    }
}
