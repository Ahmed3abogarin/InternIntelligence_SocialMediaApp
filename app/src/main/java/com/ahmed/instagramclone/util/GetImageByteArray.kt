package com.ahmed.instagramclone.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import java.io.ByteArrayOutputStream

fun getImageByteArray(context: Context, imageUri: Uri): ByteArray {
    val stream = ByteArrayOutputStream()

    val source = ImageDecoder.createSource(context.contentResolver, imageUri)
    val imageBmp = ImageDecoder.decodeBitmap(source)

    imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)

    return stream.toByteArray()
}
