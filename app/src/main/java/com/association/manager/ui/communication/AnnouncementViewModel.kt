package com.association.manager.ui.communication

import android.app.Application
import androidx.lifecycle.*
import com.association.manager.AssociationApp
import com.association.manager.data.model.Announcement
import kotlinx.coroutines.launch

class AnnouncementViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as AssociationApp).announcementRepository

    val allAnnouncements: LiveData<List<Announcement>> = repository.allAnnouncements
    val unreadCount: LiveData<Int> = repository.unreadCount

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    fun insert(announcement: Announcement) = viewModelScope.launch {
        repository.insert(announcement)
        _operationResult.value = "Annonce publiée"
    }

    fun update(announcement: Announcement) = viewModelScope.launch {
        repository.update(announcement)
        _operationResult.value = "Annonce mise à jour"
    }

    fun delete(announcement: Announcement) = viewModelScope.launch {
        repository.delete(announcement)
        _operationResult.value = "Annonce supprimée"
    }

    fun markAsRead(id: Long) = viewModelScope.launch {
        repository.markAsRead(id)
    }

    fun getAnnouncementById(id: Long): LiveData<Announcement?> =
        repository.getAnnouncementById(id)

    fun clearOperationResult() {
        _operationResult.value = null
    }
}
