package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.EventDao
import com.association.manager.data.model.Event
import com.association.manager.data.model.EventStatus

class EventRepository(private val eventDao: EventDao) {

    val allEvents: LiveData<List<Event>> = eventDao.getAllEvents()
    val eventCount: LiveData<Int> = eventDao.getEventCount()
    val upcomingEvents: LiveData<List<Event>> = eventDao.getUpcomingEvents()

    suspend fun insert(event: Event): Long = eventDao.insert(event)

    suspend fun update(event: Event) = eventDao.update(event)

    suspend fun delete(event: Event) = eventDao.delete(event)

    fun getEventById(id: Long): LiveData<Event?> = eventDao.getEventById(id)

    fun getEventsByStatus(status: EventStatus): LiveData<List<Event>> =
        eventDao.getEventsByStatus(status)

    fun searchEvents(query: String): LiveData<List<Event>> =
        eventDao.searchEvents(query)
}
