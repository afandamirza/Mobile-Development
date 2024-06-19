package com.bangkit.recyclopedia.view.takephoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MM-yyyy"
private const val MAX_SIZE = 1000000
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun reduceFileImage(file: File): File {
    var bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAX_SIZE && compressQuality > 0)

    if (compressQuality <= 0) {
        // Jika kualitas kompresi mencapai 0 dan ukuran masih besar, ubah dimensi gambar
        val scaleFactor = Math.sqrt((MAX_SIZE / streamLength).toDouble())
        val scaledWidth = (bitmap.width * scaleFactor).toInt()
        val scaledHeight = (bitmap.height * scaleFactor).toInt()
        bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
    }

    // Tulis gambar yang terkompresi kembali ke file
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, outputStream)
    outputStream.flush()
    outputStream.close()

    return file
}