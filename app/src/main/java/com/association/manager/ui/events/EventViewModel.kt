package com.association.manager.ui.events

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.Event
import com.association.manager.data.model.EventStatus
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as AssociationApp).eventRepository

    val allEvents: LiveData<List<Event>> = repository.allEvents
    val upcomingEvents: LiveData<List<Event>> = repository.upcomingEvents
    val eventCount: LiveData<Int> = repository.eventCount

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
        _operationResult.value = "Événement créé avec succès"
    }

    fun update(event: Event) = viewModelScope.launch {
        repository.update(event)
        _operationResult.value = "Événement mis à jour"
    }

    fun delete(event: Event) = viewModelScope.launch {
        repository.delete(event)
        _operationResult.value = "Événement supprimé"
    }

    fun getEventById(id: Long): LiveData<Event?> = repository.getEventById(id)

    fun getEventsByStatus(status: EventStatus): LiveData<List<Event>> =
        repository.getEventsByStatus(status)

    fun searchEvents(query: String): LiveData<List<Event>> =
        repository.searchEvents(query)

    fun clearOperationResult() {
        _operationResult.value = null
    }
}
