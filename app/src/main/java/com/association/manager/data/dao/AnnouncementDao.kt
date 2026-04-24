package com.association.manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.association.manager.data.model.Announcement
import com.association.manager.data.model.AnnouncementPriority

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(announcement: Announcement): Long

    @Update
    suspend fun update(announcement: Announcement)

    @Delete
    suspend fun delete(announcement: Announcement)

    @Query("SELECT * FROM announcements ORDER BY createdAt DESC")
    fun getAllAnnouncements(): LiveData<List<Announcement>>

    @Query("SELECT * FROM announcements WHERE id = :id")
    fun getAnnouncementById(id: Long): LiveData<Announcement?>

    @Query("SELECT * FROM announcements WHERE priority = :priority ORDER BY createdAt DESC")
    fun getAnnouncementsByPriority(priority: AnnouncementPriority): LiveData<List<Announcement>>

    @Query("SELECT COUNT(*) FROM announcements WHERE isRead = 0")
    fun getUnreadCount(): LiveData<Int>

    @Query("UPDATE announcements SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("SELECT COUNT(*) FROM announcements")
    fun getAnnouncementCount(): LiveData<Int>
}
