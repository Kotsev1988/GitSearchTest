package com.example.gitsearchtest.presentation.archives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearchtest.domain.use_cases.ArchiveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArchivesViewModel @Inject constructor(
    private val archives: ArchiveUseCase,
) : ViewModel() {

    private var getJob: Job? = null
    private val _state = MutableStateFlow(ArchiveState())
    val state = _state.asStateFlow()

    init {
        getAllReposFromArchive()
    }

    private fun getAllReposFromArchive() {
        getJob?.cancel()
        getJob = archives.getAllReposFromDB()
            .onEach {
                _state.value = state.value.copy(
                    lisOfRepos = it
                )
            }.launchIn(viewModelScope)
    }
}