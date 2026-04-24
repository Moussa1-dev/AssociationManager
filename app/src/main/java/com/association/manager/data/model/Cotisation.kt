package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cotisations")
data class Cotisation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val memberId: Long,
    val amount: Double,
    val dueDate: Long,
    val paidDate: Long? = null,
    val status: CotisationStatus = CotisationStatus.PENDING,
    val period: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class CotisationStatus {
    PENDING,
    PAID,
    OVERDUE,
    WAIVED;

    fun displayName(): String = when (this) {
        PENDING -> "En attente"
        PAID -> "Payée"
        OVERDUE -> "En retard"
        WAIVED -> "Exonérée"
    }
}
