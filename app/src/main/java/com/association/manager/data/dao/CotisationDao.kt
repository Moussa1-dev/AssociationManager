package com.association.manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.association.manager.data.model.Cotisation
import com.association.manager.data.model.CotisationStatus

@Dao
interface CotisationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cotisation: Cotisation): Long

    @Update
    suspend fun update(cotisation: Cotisation)

    @Delete
    suspend fun delete(cotisation: Cotisation)

    @Query("SELECT * FROM cotisations ORDER BY dueDate DESC")
    fun getAllCotisations(): LiveData<List<Cotisation>>

    @Query("SELECT * FROM cotisations WHERE id = :id")
    fun getCotisationById(id: Long): LiveData<Cotisation?>

    @Query("SELECT * FROM cotisations WHERE memberId = :memberId ORDER BY dueDate DESC")
    fun getCotisationsByMember(memberId: Long): LiveData<List<Cotisation>>

    @Query("SELECT * FROM cotisations WHERE status = :status ORDER BY dueDate DESC")
    fun getCotisationsByStatus(status: CotisationStatus): LiveData<List<Cotisation>>

    @Query("SELECT SUM(amount) FROM cotisations WHERE status = 'PAID'")
    fun getTotalPaidCotisations(): LiveData<Double?>

    @Query("SELECT SUM(amount) FROM cotisations WHERE status = 'PENDING' OR status = 'OVERDUE'")
    fun getTotalPendingCotisations(): LiveData<Double?>

    @Query("SELECT COUNT(*) FROM cotisations WHERE status = 'OVERDUE'")
    fun getOverdueCotisationCount(): LiveData<Int>
}
