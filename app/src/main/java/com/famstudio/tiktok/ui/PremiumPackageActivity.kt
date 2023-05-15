package com.famstudio.tiktok.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.famstudio.tiktok.R
import com.famstudio.tiktok.databinding.ActivityMainBinding
import com.famstudio.tiktok.databinding.ActivityPremiumPackageBinding

class PremiumPackageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPremiumPackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView10.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}