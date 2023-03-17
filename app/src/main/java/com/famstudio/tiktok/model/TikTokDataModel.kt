package com.famstudio.tiktok.model

data class TikTokDataModel(
    val code: Int,
    val `data`: Data,
    val msg: String,
    val processed_time: Double
)