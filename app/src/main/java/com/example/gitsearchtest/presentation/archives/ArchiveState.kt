package com.example.gitsearchtest.presentation.archives

import com.example.gitsearchtest.domain.model.ArchiveRoomEntity

data class ArchiveState(
    val lisOfRepos: List<ArchiveRoomEntity> = emptyList()
)