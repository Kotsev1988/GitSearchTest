package com.example.gitsearchtest.domain.repository

import com.example.gitsearchtest.domain.model.ArchiveRoomEntity
import kotlinx.coroutines.flow.Flow

interface IArchiveRepos {
    suspend fun insertToDB(archiveRoomEntity: ArchiveRoomEntity)
    fun getAllReposFromDB(): Flow<List<ArchiveRoomEntity>>
}