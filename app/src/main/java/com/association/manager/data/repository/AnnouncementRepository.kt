package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.AnnouncementDao
import com.association.manager.data.model.Announcement
import com.association.manager.data.model.AnnouncementPriority

class AnnouncementRepository(private val announcementDao: AnnouncementDao) {

    val allAnnouncements: LiveData<List<Announcement>> = announcementDao.getAllAnnouncements()
    val unreadCount: LiveData<Int> = announcementDao.getUnreadCount()
    val announcementCount: LiveData<Int> = announcementDao.getAnnouncementCount()

    suspend fun insert(announcement: Announcement): Long = announcementDao.insert(announcement)

    suspend fun update(announcement: Announcement) = announcementDao.update(announcement)

    suspend fun delete(announcement: Announcement) = announcementDao.delete(announcement)

    fun getAnnouncementById(id: Long): LiveData<Announcement?> =
        announcementDao.getAnnouncementById(id)

    fun getAnnouncementsByPriority(priority: AnnouncementPriority): LiveData<List<Announcement>> =
        announcementDao.getAnnouncementsByPriority(priority)

    suspend fun markAsRead(id: Long) = announcementDao.markAsRead(id)
}
