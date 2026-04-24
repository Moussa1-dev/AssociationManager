package com.association.manager

import android.app.Application
import com.association.manager.data.database.AppDatabase
import com.association.manager.data.repository.*

class AssociationApp : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    val memberRepository by lazy { MemberRepository(database.memberDao()) }
    val eventRepository by lazy { EventRepository(database.eventDao()) }
    val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    val cotisationRepository by lazy { CotisationRepository(database.cotisationDao()) }
    val documentRepository by lazy { DocumentRepository(database.documentDao()) }
    val voteRepository by lazy { VoteRepository(database.voteDao()) }
    val announcementRepository by lazy { AnnouncementRepository(database.announcementDao()) }
}
