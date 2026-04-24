package com.association.manager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.association.manager.data.dao.*
import com.association.manager.data.model.*

@Database(
    entities = [
        Member::class,
        Event::class,
        Transaction::class,
        Cotisation::class,
        Document::class,
        Vote::class,
        VoteResponse::class,
        Announcement::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao
    abstract fun eventDao(): EventDao
    abstract fun transactionDao(): TransactionDao
    abstract fun cotisationDao(): CotisationDao
    abstract fun documentDao(): DocumentDao
    abstract fun voteDao(): VoteDao
    abstract fun announcementDao(): AnnouncementDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "association_manager_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
