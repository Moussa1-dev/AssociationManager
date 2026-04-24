package com.association.manager.ui.documents

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.Document
import com.association.manager.data.model.DocumentType
import kotlinx.coroutines.launch

class DocumentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as AssociationApp).documentRepository

    val allDocuments: LiveData<List<Document>> = repository.allDocuments
    val documentCount: LiveData<Int> = repository.documentCount

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    fun insert(document: Document) = viewModelScope.launch {
        repository.insert(document)
        _operationResult.value = "Document ajouté"
    }

    fun update(document: Document) = viewModelScope.launch {
        repository.update(document)
        _operationResult.value = "Document mis à jour"
    }

    fun delete(document: Document) = viewModelScope.launch {
        repository.delete(document)
        _operationResult.value = "Document supprimé"
    }

    fun getDocumentById(id: Long): LiveData<Document?> = repository.getDocumentById(id)

    fun getDocumentsByType(type: DocumentType): LiveData<List<Document>> =
        repository.getDocumentsByType(type)

    fun searchDocuments(query: String): LiveData<List<Document>> =
        repository.searchDocuments(query)

    fun clearOperationResult() {
        _operationResult.value = null
    }
}
