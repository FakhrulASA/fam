package com.famstudio.tiktok.ui

import android.R
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.famstudio.tiktok.databinding.ActivityDownloadAndViewerPageBinding


class DownloadAndViewerPage : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadAndViewerPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadAndViewerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val videoView = binding.videoView

        // Uri object to refer the
        // resource from the videoUrl

        // Uri object to refer the
        // resource from the videoUrl
        val uri: Uri = Uri.parse(intent.extras!!.get("URL").toString())

        // sets the resource from the
        // videoUrl to the videoView

        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoURI(uri)

        // creating object of
        // media controller class

        // creating object of
        // media controller class
        val mediaController = MediaController(this)

        // sets the anchor view
        // anchor view for the videoView

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView)

        // sets the media player to the videoView

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView)

        // sets the media controller to the videoView

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController)

        // starts the video

        // starts the video
        videoView.start()
    }
}