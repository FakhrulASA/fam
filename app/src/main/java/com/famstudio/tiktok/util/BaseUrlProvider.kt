package com.famstudio.tiktok.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*


object BaseUrlProvider {
    fun provideBaseURL():String{
        return "https://tiktok-video-no-watermark2.p.rapidapi.com";
    }
    fun getAuthorizationKey():String{
        return "ebcf947ed9mshbcf63a69a577529p10403cjsn73e27f724e87"
    }
    fun getAPIHost():String{
        return "tiktok-video-no-watermark2.p.rapidapi.com"
    }

    fun getSignInId():String = "907214949757-744aukhhjt3vaasgluffdc9h37ktn23e.apps.googleusercontent.com"
    fun getInterCeptor():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return client;
    }
    fun getMimeType(uri: Uri,context:Context): String? {
        var mimeType: String? = null
        mimeType = if (ContentResolver.SCHEME_CONTENT == uri.getScheme()) {
            val cr: ContentResolver = context.contentResolver
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri
                    .toString()
            )
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.lowercase(Locale.getDefault())
            )
        }
        return mimeType
    }
}