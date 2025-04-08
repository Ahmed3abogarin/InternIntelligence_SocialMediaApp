package com.ahmed.instagramclone.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme
import com.ahmed.instagramclone.util.toFormattedTime

@Composable
fun MessageCard(userImage: String, message: Message, isMine: Boolean) {
    Log.v("CHATMESSAGE", message.isMine.toString())
    val context = LocalContext.current

    val messageColor = if (isMine) {
        Color.Green
    } else {
        Color.LightGray
    }

    val messageShape = if (isMine) {
        RoundedCornerShape(
            topStart = 50.dp,
            bottomStart = 50.dp,
            bottomEnd = 50.dp
        )
    } else {
        RoundedCornerShape(
            topEnd = 50.dp,
            bottomStart = 50.dp,
            bottomEnd = 50.dp
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {

        if (!isMine) {
            AsyncImage(
                model = ImageRequest.Builder(context).data(userImage).build(),
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Red),
                contentScale = ContentScale.Crop,
                contentDescription = "user image"
            )
        }

        Spacer(modifier = Modifier.width(7.dp))
        Column(
            modifier = Modifier
                .padding(
                    end = if (!isMine) 60.dp else 1.dp,
                    top = if (!isMine) 30.dp else 0.dp,
                    start = if (isMine) 60.dp else 1.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .clip(messageShape)
                    .background(messageColor)
            ) {
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 13.dp, end = 13.dp, top = 4.dp, bottom = 4.dp),
                    text = message.messageTxt
                )
            }
            Text(
                modifier = Modifier.align(if (isMine) Alignment.Start else Alignment.End),
                text = message.time.toFormattedTime(),
                fontSize = 9.sp
            )
        }


    }
}

@Preview
@Composable
fun MessagePreview() {
    InstagramCloneTheme {
        MessageCard(
            userImage = "",
            message = Message(messageTxt = "THe bottom is the like I have been sleep in the garden I saw dead man"),
            isMine = true
        )
    }
}