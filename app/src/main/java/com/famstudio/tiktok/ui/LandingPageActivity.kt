package com.famstudio.tiktok.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.famstudio.tiktok.R
import com.famstudio.tiktok.databinding.ActivityLandingPageBinding
import com.famstudio.tiktok.databinding.ActivityMainBinding

class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView12.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.imageButton.setOnClickListener {
            startActivity(Intent(this,PremiumPackageActivity::class.java))
        }
    }
}