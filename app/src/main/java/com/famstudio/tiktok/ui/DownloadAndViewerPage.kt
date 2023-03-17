package com.famstudio.tiktok.ui

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.famstudio.tiktok.databinding.ActivityDownloadAndViewerPageBinding
import kotlin.math.log


class DownloadAndViewerPage : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadAndViewerPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadAndViewerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val videoView = binding.videoView
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(intent.extras!!.get("URL").toString())

        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)

        mediaController.setAnchorView(videoView)

        mediaController.setMediaPlayer(videoView)

        videoView.setMediaController(mediaController)


        videoView.start()

        val url = intent.extras!!.get("URL").toString()
        val title = intent.extras!!.get("NAME").toString()



        binding.button5.setOnClickListener {

            val request = DownloadManager.Request(url.toUri())
                .setMimeType("video/mp4")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(title)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
            downloadManager.enqueue(request)
        }

    }

}