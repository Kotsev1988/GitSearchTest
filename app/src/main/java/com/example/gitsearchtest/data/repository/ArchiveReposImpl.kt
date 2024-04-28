package com.example.gitsearchtest.data.repository


import com.example.gitsearchtest.data.data_source.Database
import com.example.gitsearchtest.domain.model.ArchiveRoomEntity
import com.example.gitsearchtest.domain.repository.IArchiveRepos
import kotlinx.coroutines.flow.Flow

class ArchiveReposImpl(private val db: Database): IArchiveRepos {
    override suspend fun insertToDB(archiveRoomEntity: ArchiveRoomEntity) = db.archiveRoomDao.insert(archiveRoomEntity)

    override fun getAllReposFromDB(): Flow<List<ArchiveRoomEntity>> = db.archiveRoomDao.getAll()
}