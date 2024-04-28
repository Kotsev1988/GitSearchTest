package com.example.gitsearchtest.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gitsearchtest.domain.model.ArchiveRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchiveRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(archive: ArchiveRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg archive: ArchiveRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(archive: List<ArchiveRoomEntity>)

    @Update
    fun update(archive: ArchiveRoomEntity)

    @Update
    fun update(vararg archive: ArchiveRoomEntity)

    @Update
    fun update(archive: List<ArchiveRoomEntity>)

    @Delete
    suspend fun delete(archive: ArchiveRoomEntity)

    @Delete
    suspend fun delete(vararg archive: ArchiveRoomEntity)

    @Delete
    suspend fun delete(archive: List<ArchiveRoomEntity>)

    @Query("SELECT * FROM ArchiveRoomEntity ")
    fun getAll(): Flow<List<ArchiveRoomEntity>>
}