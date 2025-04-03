package com.ahmed.instagramclone.presentation.new_post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahmed.instagramclone.util.Resource

@Composable
fun NewPostScreen(state: Resource<Unit>?,event: (NewPostEvent) -> Unit) {
    val context = LocalContext.current

    when (state) {
        is Resource.Loading -> {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }

        is Resource.Success -> {

            Toast.makeText(context, "Post uploaded ::))", Toast.LENGTH_SHORT).show()
        }

        is Resource.Error -> {
            Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show()
        }

        else -> Unit
    }


    var image by remember { mutableStateOf<Uri?>(null) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        image = it
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        image?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(410.dp),
                painter = rememberAsyncImagePainter(it),
                contentDescription = null
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 12.dp),
            onClick = {
                if (image !=null) {
                    event(NewPostEvent.UploadPost(image!!))
                }

            }) {
            Text(text = "Upload post")
        }


    }
}



