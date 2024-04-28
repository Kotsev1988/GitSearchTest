package com.example.gitsearchtest.domain.use_cases

data class ArchiveUseCase(
    val getAllReposFromDB: GetArchives,
    val addArchiveToDB: AddRepoArchive
)
