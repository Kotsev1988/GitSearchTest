package com.example.gitsearchtest.domain.use_cases

import com.example.gitsearchtest.domain.model.ArchiveRoomEntity
import com.example.gitsearchtest.domain.repository.IArchiveRepos
import kotlinx.coroutines.flow.Flow

class GetArchives(private  val repository: IArchiveRepos) {
    operator fun invoke(): Flow<List<ArchiveRoomEntity>> {
        return repository.getAllReposFromDB()
    }
}