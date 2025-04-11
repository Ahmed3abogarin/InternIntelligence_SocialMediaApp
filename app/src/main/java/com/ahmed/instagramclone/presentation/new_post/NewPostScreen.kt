package com.ahmed.instagramclone.presentation.new_post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahmed.instagramclone.ui.theme.Color4
import com.ahmed.instagramclone.util.Resource

@Composable
fun NewPostScreen(state: Resource<Unit>?, event: (NewPostEvent) -> Unit) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 3.dp)
    ) {
        image?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(410.dp),
                painter = rememberAsyncImagePainter(it),
                contentDescription = null
            )
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            placeholder = { Text(text = "Description") },
            value = "",
            onValueChange = {},
            minLines = 10,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    launcher.launch("image/*")
                }) {
                Text(text = "Pick image")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    if (image != null) {
                        event(NewPostEvent.UploadPost(image!!))
                    } else {
                        Toast.makeText(context, "Choose an image", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color4)
            ) {
                Text(text = "Upload post")
            }
        }
    }
}



