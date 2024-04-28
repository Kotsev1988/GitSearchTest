package com.example.gitsearchtest.presentation.search

sealed class SearchAppState {
    data class EnteredComment(val userName: String): SearchAppState()
}