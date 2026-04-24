package com.association.manager.ui.finance

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.*
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as AssociationApp
    private val transactionRepository = app.transactionRepository
    private val cotisationRepository = app.cotisationRepository

    val allTransactions: LiveData<List<Transaction>> = transactionRepository.allTransactions
    val totalIncome: LiveData<Double?> = transactionRepository.totalIncome
    val totalExpenses: LiveData<Double?> = transactionRepository.totalExpenses
    val balance: LiveData<Double> = transactionRepository.balance

    val allCotisations: LiveData<List<Cotisation>> = cotisationRepository.allCotisations
    val totalPaidCotisations: LiveData<Double?> = cotisationRepository.totalPaid
    val totalPendingCotisations: LiveData<Double?> = cotisationRepository.totalPending

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepository.insert(transaction)
        _operationResult.value = "Transaction ajoutée"
    }

    fun updateTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepository.update(transaction)
        _operationResult.value = "Transaction mise à jour"
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepository.delete(transaction)
        _operationResult.value = "Transaction supprimée"
    }

    fun insertCotisation(cotisation: Cotisation) = viewModelScope.launch {
        cotisationRepository.insert(cotisation)
        _operationResult.value = "Cotisation ajoutée"
    }

    fun updateCotisation(cotisation: Cotisation) = viewModelScope.launch {
        cotisationRepository.update(cotisation)
        _operationResult.value = "Cotisation mise à jour"
    }

    fun deleteCotisation(cotisation: Cotisation) = viewModelScope.launch {
        cotisationRepository.delete(cotisation)
        _operationResult.value = "Cotisation supprimée"
    }

    fun getCotisationsByMember(memberId: Long): LiveData<List<Cotisation>> =
        cotisationRepository.getCotisationsByMember(memberId)

    fun getTransactionById(id: Long): LiveData<Transaction?> =
        transactionRepository.getTransactionById(id)

    fun clearOperationResult() {
        _operationResult.value = null
    }
}
