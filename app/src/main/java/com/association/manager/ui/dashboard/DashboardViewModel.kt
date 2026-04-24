package com.association.manager.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.association.manager.AssociationApp

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as AssociationApp

    val memberCount: LiveData<Int> = app.memberRepository.memberCount
    val eventCount: LiveData<Int> = app.eventRepository.eventCount
    val balance: LiveData<Double> = app.transactionRepository.balance
    val unreadAnnouncements: LiveData<Int> = app.announcementRepository.unreadCount
    val activeVotes: LiveData<Int> = app.voteRepository.activeVoteCount
    val documentCount: LiveData<Int> = app.documentRepository.documentCount
    val upcomingEvents = app.eventRepository.upcomingEvents
    val overdueCotisations: LiveData<Int> = app.cotisationRepository.overdueCount
}
