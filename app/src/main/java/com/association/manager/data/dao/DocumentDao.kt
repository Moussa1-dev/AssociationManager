package com.association.manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.association.manager.data.model.Document
import com.association.manager.data.model.DocumentType

@Dao
interface DocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(document: Document): Long

    @Update
    suspend fun update(document: Document)

    @Delete
    suspend fun delete(document: Document)

    @Query("SELECT * FROM documents ORDER BY createdAt DESC")
    fun getAllDocuments(): LiveData<List<Document>>

    @Query("SELECT * FROM documents WHERE id = :id")
    fun getDocumentById(id: Long): LiveData<Document?>

    @Query("SELECT * FROM documents WHERE type = :type ORDER BY createdAt DESC")
    fun getDocumentsByType(type: DocumentType): LiveData<List<Document>>

    @Query("SELECT COUNT(*) FROM documents")
    fun getDocumentCount(): LiveData<Int>

    @Query("SELECT * FROM documents WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchDocuments(query: String): LiveData<List<Document>>
}
