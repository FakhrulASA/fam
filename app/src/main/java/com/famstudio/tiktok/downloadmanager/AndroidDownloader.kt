package com.famstudio.tiktok.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.famstudio.tiktok.util.BaseUrlProvider.getMimeType

class AndroidDownloader(
    private val context: Context,
    private val title: String
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadAudioFile(url: String): Long {
        val request = DownloadManager.Request(url.replace("\\", "").trim().toUri())
            .setMimeType("audio/*")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(title)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
        return downloadManager.enqueue(request)
    }
    override fun downloadVideoFile(url: String): Long {
        val request = DownloadManager.Request(url.replace("\\", "").trim().toUri())
            .setMimeType("video/*")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(title)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
        return downloadManager.enqueue(request)
    }
}