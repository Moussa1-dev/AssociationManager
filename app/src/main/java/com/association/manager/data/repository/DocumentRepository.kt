package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.DocumentDao
import com.association.manager.data.model.Document
import com.association.manager.data.model.DocumentType

class DocumentRepository(private val documentDao: DocumentDao) {

    val allDocuments: LiveData<List<Document>> = documentDao.getAllDocuments()
    val documentCount: LiveData<Int> = documentDao.getDocumentCount()

    suspend fun insert(document: Document): Long = documentDao.insert(document)

    suspend fun update(document: Document) = documentDao.update(document)

    suspend fun delete(document: Document) = documentDao.delete(document)

    fun getDocumentById(id: Long): LiveData<Document?> = documentDao.getDocumentById(id)

    fun getDocumentsByType(type: DocumentType): LiveData<List<Document>> =
        documentDao.getDocumentsByType(type)

    fun searchDocuments(query: String): LiveData<List<Document>> =
        documentDao.searchDocuments(query)
}
