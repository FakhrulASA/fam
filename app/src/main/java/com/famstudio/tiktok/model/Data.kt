package com.famstudio.tiktok.model

data class Data(
    val author: Author,
    val aweme_id: String,
    val comment_count: Int,
    val cover: String,
    val create_time: Int,
    val digg_count: Int,
    val download_count: Int,
    val duration: Int,
    val id: String,
    val music: String,
    val music_info: MusicInfo,
    val origin_cover: String,
    val play: String,
    val play_count: Int,
    val region: String,
    val share_count: Int,
    val size: Int,
    val title: String,
    val wm_size: Int,
    val wmplay: String
)