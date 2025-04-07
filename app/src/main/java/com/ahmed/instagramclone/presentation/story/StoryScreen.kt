package com.ahmed.instagramclone.presentation.story

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.presentation.components.SearchCard


@Composable
fun StoryScreen(story: StoryWithAuthor, navigateToUser: (String) -> Unit) {
    val isPlaying = remember { mutableStateOf(true) }
    Box {
        ReelPlayer(story.story.videoUrl, isPlaying)

        Log.v("COSETTE", story.author.firstName)
        Row (modifier = Modifier.statusBarsPadding().padding(top = 12.dp)){

        }
        SearchCard(user = story.author, navigateToUser = { navigateToUser(story.author.userId) })


    }

}