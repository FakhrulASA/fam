package com.famstudio.tiktok.downloadmanager

interface Downloader {
    fun downloadVideoFile(url: String): Long
    fun downloadAudioFile(url: String): Long
}