package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.TransactionDao
import com.association.manager.data.model.Transaction
import com.association.manager.data.model.TransactionType

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    val totalIncome: LiveData<Double?> = transactionDao.getTotalIncome()
    val totalExpenses: LiveData<Double?> = transactionDao.getTotalExpenses()
    val balance: LiveData<Double> = transactionDao.getBalance()
    val transactionCount: LiveData<Int> = transactionDao.getTransactionCount()

    suspend fun insert(transaction: Transaction): Long = transactionDao.insert(transaction)

    suspend fun update(transaction: Transaction) = transactionDao.update(transaction)

    suspend fun delete(transaction: Transaction) = transactionDao.delete(transaction)

    fun getTransactionById(id: Long): LiveData<Transaction?> =
        transactionDao.getTransactionById(id)

    fun getTransactionsByType(type: TransactionType): LiveData<List<Transaction>> =
        transactionDao.getTransactionsByType(type)

    fun getTransactionsByDateRange(startDate: Long, endDate: Long): LiveData<List<Transaction>> =
        transactionDao.getTransactionsByDateRange(startDate, endDate)
}
