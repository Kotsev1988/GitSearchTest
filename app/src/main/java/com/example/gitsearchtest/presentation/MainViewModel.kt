package com.example.gitsearchtest.presentation

import androidx.lifecycle.ViewModel
import com.example.gitsearchtest.domain.model.ArchiveRoomEntity
import com.example.gitsearchtest.domain.use_cases.ArchiveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val archives: ArchiveUseCase,
) : ViewModel() {

    suspend fun saveArchive(repoName: String, userName: String){
        archives.addArchiveToDB(
            ArchiveRoomEntity(
                repoName = repoName,
                userName = userName
            )
        )
    }
}