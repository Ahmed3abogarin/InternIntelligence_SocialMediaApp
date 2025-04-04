package com.ahmed.instagramclone.presentation.reels


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ahmed.instagramclone.domain.model.Reel
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.util.Resource

@Composable
fun ReelsScreen(state: Resource<List<Reel>>?){
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color.Red)
        ) {
            state?.data?.let {
                items(it) { video ->

                    Log.v("TAGY",video.videoUrl)
                        ReelPlayer(video.videoUrl)

                }
            }

        }

}