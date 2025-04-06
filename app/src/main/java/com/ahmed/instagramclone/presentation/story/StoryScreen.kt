package com.ahmed.instagramclone.presentation.story

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.util.Resource

@Composable
fun StoryScreen(state: Resource<List<Story>>?) {
    val ss = remember { mutableStateOf(false) }
    when (state) {
        is Resource.Success -> {
            state.data?.let {
                ReelPlayer(state.data[0].videoUrl, ss)
            }

        }

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Errororororo")
            }
        }

        else -> Unit

    }


}