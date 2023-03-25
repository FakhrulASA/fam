package com.famstudio.tiktok.ui

import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.famstudio.tiktok.databinding.ActivityDownloadAndViewerPageBinding
import com.famstudio.tiktok.downloadmanager.AndroidDownloader
import com.famstudio.tiktok.util.*


class DownloadAndViewerPage : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadAndViewerPageBinding
    private val vm: VideoDownloadViewModel by viewModels()
    private var url: String = ""
    private var audioUrl: String = ""
    private var name: String = ""
    private var id: String = ""
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadAndViewerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        vm.isLoading.observe(this) {
            if (it) {
                binding.progressBar2.visibility = View.VISIBLE
            } else {
                binding.progressBar2.visibility = View.GONE
            }
        }
        url = sh.getString(VIDEO_URL, "").toString()
        audioUrl = sh.getString(AUDIO_URL, "").toString()
        name = sh.getString(PROFILE_NAME, "").toString()
        id = sh.getString(PROFILE_ID, "").toString()
        title = sh.getString(VIDEO_TITLE, "").toString()
        val videoView = binding.videoView
        videoView.setVideoURI(url.toUri())
        val mediaController = MediaController(this@DownloadAndViewerPage)
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
        binding.button5.setOnClickListener {
            var androidDownloader = AndroidDownloader(this, title)
            androidDownloader.downloadVideoFile(url)
        }

        binding.button.setOnClickListener {
            var androidDownloader = AndroidDownloader(this@DownloadAndViewerPage, title)

            androidDownloader.downloadAudioFile(audioUrl)
        }
    }

}