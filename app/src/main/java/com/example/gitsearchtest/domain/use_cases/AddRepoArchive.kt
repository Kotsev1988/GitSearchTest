package com.example.gitsearchtest.domain.use_cases

import com.example.gitsearchtest.domain.model.ArchiveRoomEntity
import com.example.gitsearchtest.domain.repository.IArchiveRepos

class AddRepoArchive(private  val repository: IArchiveRepos) {
    suspend operator fun invoke(gitRepo: ArchiveRoomEntity){
        repository.insertToDB(gitRepo)
    }
}