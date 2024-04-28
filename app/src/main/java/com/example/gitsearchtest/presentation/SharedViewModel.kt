package com.example.gitsearchtest.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

) : ViewModel() {
    private val _repoName= MutableStateFlow("")
    val repoName =_repoName.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName =_userName.asStateFlow()

    fun setSharedData(repoName: String, userName: String) {
        _repoName.update { repoName}
        _userName.update { userName }
    }
}