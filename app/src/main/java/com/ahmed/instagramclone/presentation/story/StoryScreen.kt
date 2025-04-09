package com.ahmed.instagramclone.presentation.story

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.presentation.components.SearchCard


@Composable
fun StoryScreen(
    story: StoryWithAuthor,
    navigateToUser: (String) -> Unit,
    navigateToUp: () -> Unit,
) {
    val isPlaying = remember { mutableStateOf(true) }
    Box(modifier = Modifier.statusBarsPadding()) {
        ReelPlayer(story.story.videoUrl, isPlaying)

        Log.v("COSETTE", story.author.firstName)
        Row(modifier = Modifier.padding(top = 12.dp)) {
            IconButton(onClick = { navigateToUp() }) {
                Icon(
                    modifier = Modifier.size(38.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            SearchCard(
                user = story.author,
                navigateToUser = { navigateToUser(story.author.userId) })
        }
    }
}