package com.ahmed.instagramclone.presentation.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun ReelPlayer(videoUrl: String,isPlaying: MutableState<Boolean>) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            setHandleAudioBecomingNoisy(false)
            prepare()
            playWhenReady = true
            if (isPlaying.value){
                stop()
            }

            // Add listener for smoother looping
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        seekTo(0)
                        play()
                    }
                }
            })
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }

    }


    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .clickable { isPlaying.value = !isPlaying.value }
            .background(Color.Red),
        factory = { con ->
            FrameLayout(con).apply {
                addView(PlayerView(con).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                })
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )


//    AndroidView(
//        modifier = Modifier.fillMaxSize().background(Color.Red),
//        factory = { ctx ->
//            PlayerView(ctx).apply {
//                player = exoPlayer
//                useController = false
//                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
//
//            }
//        },
//        update = { playerView -> // Use update to configure the PlayerView
//            playerView.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        }
//    )
}