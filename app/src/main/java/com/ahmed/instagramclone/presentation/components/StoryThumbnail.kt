package com.ahmed.instagramclone.presentation.components

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun StoryThumbnail(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    var thumbnail by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(videoUrl) {
        withContext(Dispatchers.IO) {
            try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(videoUrl, HashMap())
                val frame = retriever.getFrameAtTime(0)
                thumbnail = frame
                retriever.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    if (thumbnail != null) {
        Image(
            bitmap = thumbnail!!.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
                .size(77.dp)
                .clip(CircleShape)
        )
    } else {
        // loading place holder
        Box(
            modifier = modifier
                .size(77.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}
