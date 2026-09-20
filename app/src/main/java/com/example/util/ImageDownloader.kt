package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

object ImageDownloader {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    suspend fun saveImageToGallery(
        context: Context,
        imageUrl: String,
        desiredFilename: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(imageUrl).build()
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("HTTP ${response.code}"))
            }

            val imageBytes = response.body?.bytes()
                ?: return@withContext Result.failure(Exception("Empty image data"))

            val finalName = if (desiredFilename.endsWith(".jpg", ignoreCase = true)) {
                desiredFilename
            } else {
                "$desiredFilename.jpg"
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, finalName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/ThumbGrab")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    ?: return@withContext Result.failure(Exception("Failed to create MediaStore entry"))

                resolver.openOutputStream(uri).use { output ->
                    if (output != null) {
                        output.write(imageBytes)
                        output.flush()
                    } else {
                        return@withContext Result.failure(Exception("Failed to open output stream"))
                    }
                }

                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)

                Result.success(finalName)
            } else {
                @Suppress("DEPRECATION")
                val picturesDir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "ThumbGrab"
                )
                if (!picturesDir.exists()) {
                    picturesDir.mkdirs()
                }
                val destFile = File(picturesDir, finalName)
                FileOutputStream(destFile).use { out ->
                    out.write(imageBytes)
                    out.flush()
                }
                Result.success(destFile.name)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun copyToClipboard(context: Context, text: String, label: String = "ThumbGrab URL") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    fun openUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
        }
    }
}
