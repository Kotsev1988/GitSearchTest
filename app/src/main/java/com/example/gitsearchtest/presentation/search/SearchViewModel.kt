package com.example.gitsearchtest.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearchtest.domain.model.UserRepositoriesItem
import com.example.gitsearchtest.domain.use_cases.UserReposUseCase
import com.example.gitsearchtest.domain.use_cases.UsersUseCase
import com.example.gitsearchtest.presentation.util.DownloadUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getContentsUseCase: UsersUseCase,
    private val detailInfoUseCase: UserReposUseCase,
    private val downloadUtil: DownloadUtil,
) : ViewModel() {

    private val _state = MutableStateFlow(listOf<UserRepositoriesItem>())
    private val _planComment = mutableStateOf(
        PlanTextFieldState(
            hint = "Enter comment..."
        )
    )
    val planComment: State<PlanTextFieldState> = _planComment
    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val list: MutableList<UserRepositoriesItem> = mutableListOf()

    val state = searchText
        .debounce(1000L)
        .distinctUntilChanged()
        .filter {
            it.length > 1
        }
        .onEach {
            _isSearching.update { true }
            list.clear()
        }
        .flowOn(Dispatchers.IO)
        .flatMapLatest {
            flow {
                val result = getContentsUseCase.getUsersUseCase.getUsers(it)
                if (result.isSuccessful) {
                    result.body()?.items?.take(30)?.forEach {
                        emit(detailInfoUseCase.getUserReposUseCase.getDetailInfo(it.repos_url))
                    }
                }
            }
        }
        .combine(_state) { some, _ ->
            list.addAll(some)
            list.toList()
        }
        .onEach {
            _isSearching.update { false }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun handleViewEvent(viewEvent: SearchAppState) {
        when (viewEvent) {
            is SearchAppState.EnteredComment -> {
                viewEvent.userName.let {
                    _searchText.value = it
                    _planComment.value = planComment.value.copy(
                        text = it
                    )
                }
            }
        }
    }

    fun downloadZip(owner: String, repoName: String) {
        downloadUtil.download(owner, repoName)
    }
}