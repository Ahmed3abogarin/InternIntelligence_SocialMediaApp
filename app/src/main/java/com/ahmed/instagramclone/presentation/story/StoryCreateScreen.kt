package com.ahmed.instagramclone.presentation.story

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.util.Resource

@Composable
fun StoryCreateScreen(storyViewModel: StoryCreateViewModel) {
    var video by remember { mutableStateOf<Uri?>(null) }
    val isPlaying = remember { mutableStateOf(false) }

    val context = LocalContext.current


    when (storyViewModel.state.value) {
        is Resource.Loading -> {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }

        is Resource.Success -> {

            Toast.makeText(context, "Story uploaded ::))", Toast.LENGTH_SHORT).show()
        }

        is Resource.Error -> {
            Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show()
        }

        else -> Unit
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        video = it
    }

    LaunchedEffect(Unit) {
        launcher.launch("video/*")

    }

    Box(modifier = Modifier.fillMaxSize()) {
        video?.let {
            ReelPlayer(video!!, isPlaying = isPlaying)
        }


        Button(onClick = {
            if (video != null) {
                storyViewModel.uploadStory(video!!)
            }
        }) {
            Text("Pick video ya istorah")
        }
    }
}