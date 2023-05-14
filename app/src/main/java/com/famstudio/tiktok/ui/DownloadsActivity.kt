package com.famstudio.tiktok.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.famstudio.tiktok.R
import java.io.File


class DownloadsActivity : AppCompatActivity() {
    lateinit var listView  : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloads)

        listView = findViewById<ListView>(R.id.recycler)
       getVideo()
    }

    @SuppressLint("Range")
    fun getVideo(){
        var videos : MutableList<String> = mutableListOf()
        val contentResolver = contentResolver
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = Environment.DIRECTORY_DOWNLOADS
        val selectionArgs = arrayOf(Environment.DIRECTORY_DOWNLOADS)

        val cursor: Cursor? = contentResolver.query(uri, null, selection, selectionArgs, null)

        //looping through all rows and adding to list

        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                val duration: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                val data: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                videos.add(title)

            } while (cursor.moveToNext())
        }


        Toast.makeText(this,videos.size.toString(),Toast.LENGTH_LONG).show()
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videos)
        listView.adapter = arrayAdapter

    }
}