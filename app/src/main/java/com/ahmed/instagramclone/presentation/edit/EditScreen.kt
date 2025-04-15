package com.ahmed.instagramclone.presentation.edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.util.Resource

@Composable
fun EditScreen(
    state: Resource<User?>?,
    updateState: Resource<Unit>?,
    navigateUp: () -> Unit,
    event: (EditEvent) -> Unit,
) {
    val context = LocalContext.current


    when (state) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator(color = Color.LightGray, strokeWidth = 2.dp)
            }
        }

        is Resource.Success -> {
            state.data?.let {
                EditScreenContent(it, event = event)
            }
        }

        is Resource.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(text = state.message.toString())
            }
        }

        else -> Unit
    }

    when (updateState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.LightGray, strokeWidth = 2.dp)
            }
        }

        is Resource.Success -> {
            navigateUp()
        }

        is Resource.Error -> {
            Toast.makeText(context, state?.message.toString(), Toast.LENGTH_SHORT).show()
        }

        else -> Unit
    }


}

@Composable
fun EditScreenContent(user: User, event: (EditEvent) -> Unit) {
    val context = LocalContext.current
    var image by remember { mutableStateOf<Uri?>(null) }

    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var bio by remember { mutableStateOf(user.bio) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        image = it
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .statusBarsPadding()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.clickable { launcher.launch("image/*") }) {

            if (image != null) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = null
                )

            } else {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                        .background(Color.Black),
                    model = ImageRequest.Builder(context).data(user.imagePath)
                        .error(R.drawable.profile_placeholder)
                        .placeholder(R.drawable.profile_placeholder).build(),
                    contentDescription = "user profile photo",
                    contentScale = ContentScale.Crop
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = firstName,
                onValueChange = { firstName = it },
                shape = RoundedCornerShape(14.dp),
                label = { Text(text = "First name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = { lastName = it },
                shape = RoundedCornerShape(14.dp),
                label = { Text(text = "Last name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = bio,
                onValueChange = { bio = it },
                shape = RoundedCornerShape(14.dp),
                minLines = 6,
                label = { Text(text = "Bio") }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                ),
                colors = ButtonDefaults.buttonColors(Color.Black),
                elevation = ButtonDefaults.elevatedButtonElevation(12.dp),
                onClick = {
                    event(EditEvent.UpdateUserInfo(user = user.copy(firstName = firstName, lastName = lastName, bio = bio), uri = image))

                }) {
                Text("Save", modifier = Modifier.padding(4.dp))
            }


        }


    }

}
