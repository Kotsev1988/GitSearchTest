package com.example.gitsearchtest.presentation.util

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri


class DownloadUtilImpl(
    context: Context,
) : DownloadUtil {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun download(owner: String, repoName: String): Long {

        val branch = "master"
        val archiveUrl = "https://api.github.com/repos/$owner/$repoName/{archive_format}/{/ref}"
            .replace("{archive_format}", "zipball")
            .replace("{/ref}", "$branch")

        val request = DownloadManager.Request(archiveUrl.toUri()).setMimeType("zipball")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Document")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "$repoName-$branch.zip"
            )
        return downloadManager.enqueue(request)
    }
}