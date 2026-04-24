package com.association.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val amount: Double,
    val type: TransactionType,
    val category: TransactionCategory = TransactionCategory.OTHER,
    val date: Long = System.currentTimeMillis(),
    val memberId: Long? = null,
    val receiptUri: String? = null,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class TransactionType {
    INCOME,
    EXPENSE;

    fun displayName(): String = when (this) {
        INCOME -> "Revenu"
        EXPENSE -> "Dépense"
    }
}

enum class TransactionCategory {
    COTISATION,
    DONATION,
    SUBVENTION,
    EVENT,
    EQUIPMENT,
    RENT,
    SALARY,
    SUPPLIES,
    OTHER;

    fun displayName(): String = when (this) {
        COTISATION -> "Cotisation"
        DONATION -> "Don"
        SUBVENTION -> "Subvention"
        EVENT -> "Événement"
        EQUIPMENT -> "Équipement"
        RENT -> "Loyer"
        SALARY -> "Salaire"
        SUPPLIES -> "Fournitures"
        OTHER -> "Autre"
    }
}
