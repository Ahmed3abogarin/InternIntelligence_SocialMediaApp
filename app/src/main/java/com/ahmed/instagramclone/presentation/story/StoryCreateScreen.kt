package com.ahmed.instagramclone.presentation.story

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.ui.theme.Color1
import com.ahmed.instagramclone.ui.theme.Color3
import com.ahmed.instagramclone.util.Resource

@Composable
fun StoryCreateScreen(storyViewModel: StoryCreateViewModel) {
    var video by remember { mutableStateOf<Uri?>(null) }
    val isPlaying = remember { mutableStateOf(false) }

    val context = LocalContext.current


    when (storyViewModel.state.value) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = Color1, strokeWidth = 1.5.dp)
            }
        }

        is Resource.Success -> {

            Toast.makeText(context, "Story uploaded", Toast.LENGTH_SHORT).show()
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

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding().padding(6.dp)) {
        video?.let {
            ReelPlayer(video!!, isPlaying = isPlaying)
        }


        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
            onClick = {
            if (video != null) {
                storyViewModel.uploadStory(video!!)
            }
        }, ) {
            Text("upload story", color = Color3)
        }
    }
}