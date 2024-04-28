package com.example.gitsearchtest.data.data_source

import androidx.room.RoomDatabase
import com.example.gitsearchtest.domain.model.ArchiveRoomEntity

@androidx.room.Database (entities = [ArchiveRoomEntity::class], version = 8)

abstract class Database: RoomDatabase() {
    abstract val archiveRoomDao: ArchiveRoomDao

    companion object {
        const val DB_NAME = "database.db"
    }
}