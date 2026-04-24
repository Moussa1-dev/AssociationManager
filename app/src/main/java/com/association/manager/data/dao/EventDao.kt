package com.association.manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.association.manager.data.model.Event
import com.association.manager.data.model.EventStatus

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM events ORDER BY startDate DESC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Long): LiveData<Event?>

    @Query("SELECT * FROM events WHERE status = :status ORDER BY startDate DESC")
    fun getEventsByStatus(status: EventStatus): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE startDate >= :fromDate ORDER BY startDate ASC")
    fun getUpcomingEvents(fromDate: Long = System.currentTimeMillis()): LiveData<List<Event>>

    @Query("SELECT COUNT(*) FROM events")
    fun getEventCount(): LiveData<Int>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchEvents(query: String): LiveData<List<Event>>
}
