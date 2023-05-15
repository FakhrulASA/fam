package com.famstudio.tiktok.util

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AppUtil

fun showDialogMessage(context: Context, title: String, description: String) {
    MaterialAlertDialogBuilder(context)
        // Add customization options here
        .setTitle(title).setMessage(description)
        .setPositiveButton("Continue", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        }).show()
}