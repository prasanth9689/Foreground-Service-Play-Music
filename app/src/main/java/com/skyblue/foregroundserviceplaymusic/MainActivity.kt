package com.skyblue.foregroundserviceplaymusic

import android.content.Intent
import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.skyblue.foregroundserviceplaymusic.databinding.ActivityMainBinding
import java.util.LinkedList
import java.util.SortedSet
import java.util.TreeSet


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var context = this@MainActivity
    private val TAG = "MainActivity_"
    private var allLoaded = false
    private var rowsToLoad = 0
    private var startingRow = 0

    /*
     https://medium.com/android-news/custom-gallery-for-android-af2437b227da
     https://github.com/mmobin789/Android-Custom-Gallery/blob/master/app-rx/src/main/java/rx/kt/gallery/viewmodel/GalleryViewModel.kt#L29
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestReadStoragePermission()
        loadMusicList()

        binding.start.setOnClickListener {
            val intent = Intent(context, MusicPlayerService::class.java)
            intent.putExtra("music_name", "Kattre  enthan geetham")
            startService(intent)
        }
    }

    private fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                readStorage
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } else initNow()
    }

    private fun initNow() {
        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
    }

    private fun fetchGalleryImages(context: Context, rowsPerLoad: Int): List<GalleryPicture> {
        val galleryImageUrls = LinkedList<GalleryPicture>()
        val cursor = getGalleryCursor(context)

        if (cursor != null && !allLoaded) {
            val totalRows = cursor.count

            allLoaded = rowsToLoad == totalRows
            if (rowsToLoad < rowsPerLoad) {
                rowsToLoad = rowsPerLoad
            }

            for (i in startingRow until rowsToLoad) {
                cursor.moveToPosition(i)
                val dataColumnIndex =
                    cursor.getColumnIndex(MediaStore.MediaColumns._ID) //get column index
                galleryImageUrls.add(GalleryPicture(getImageUri(cursor.getString(dataColumnIndex)).toString())) //get Image path from column index

            }
            Log.i(TAG, "TotalGallerySize : $totalRows")
            Log.i(TAG, "GalleryStart : $startingRow")
            Log.i(TAG , "GalleryEnd : $rowsToLoad")

            startingRow = rowsToLoad

            if (rowsPerLoad > totalRows || rowsToLoad >= totalRows)
                rowsToLoad = totalRows
            else {
                if (totalRows - rowsToLoad <= rowsPerLoad)
                    rowsToLoad = totalRows
                else
                    rowsToLoad += rowsPerLoad
            }

            cursor.close()
            Log.i(TAG, "PartialGallerySize ${galleryImageUrls.size}")
        }
        return galleryImageUrls
    }

    private fun getGalleryCursor(context: Context): Cursor? {
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columns = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DATE_MODIFIED)
        val orderBy = MediaStore.MediaColumns.DATE_MODIFIED //order data by modified
        return context.contentResolver
            .query(
                externalUri,
                columns,
                null,
                null,
                "$orderBy DESC"
            )//get all data in Cursor by sorting in DESC order
    }

    data class GalleryPicture(val path: String) {
        var isSelected = false
    }

    private fun getImageUri(path: String) = ContentUris.withAppendedId(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        path.toLong()
    )

    private fun loadMusicList() {
        fetchGalleryImages(context, 5)
    }
}