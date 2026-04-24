package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.CotisationDao
import com.association.manager.data.model.Cotisation
import com.association.manager.data.model.CotisationStatus

class CotisationRepository(private val cotisationDao: CotisationDao) {

    val allCotisations: LiveData<List<Cotisation>> = cotisationDao.getAllCotisations()
    val totalPaid: LiveData<Double?> = cotisationDao.getTotalPaidCotisations()
    val totalPending: LiveData<Double?> = cotisationDao.getTotalPendingCotisations()
    val overdueCount: LiveData<Int> = cotisationDao.getOverdueCotisationCount()

    suspend fun insert(cotisation: Cotisation): Long = cotisationDao.insert(cotisation)

    suspend fun update(cotisation: Cotisation) = cotisationDao.update(cotisation)

    suspend fun delete(cotisation: Cotisation) = cotisationDao.delete(cotisation)

    fun getCotisationById(id: Long): LiveData<Cotisation?> =
        cotisationDao.getCotisationById(id)

    fun getCotisationsByMember(memberId: Long): LiveData<List<Cotisation>> =
        cotisationDao.getCotisationsByMember(memberId)

    fun getCotisationsByStatus(status: CotisationStatus): LiveData<List<Cotisation>> =
        cotisationDao.getCotisationsByStatus(status)
}
