package com.example.gitsearchtest.presentation.util

interface DownloadUtil {
    fun download(owner: String, repoName: String): Long
}