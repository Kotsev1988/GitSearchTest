package com.example.gitsearchtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArchiveRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val repoName: String?=null,
    val userName: String?=null,
)
