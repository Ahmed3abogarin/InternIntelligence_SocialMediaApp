package com.ahmed.instagramclone.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme
import com.ahmed.instagramclone.ui.theme.ShimmerColor
import com.ahmed.instagramclone.util.toFormattedTime

@Composable
fun MessageCard(userImage: String, message: Message, isMine: Boolean) {
    Log.v("CHATMESSAGE", message.isMine.toString())
    val context = LocalContext.current

    val messageColor = if (isMine) {
        listOf(
            Color(0xFFB726FF),
            Color(0xFF7C1EFF)
//            Color7,
//            Color4
//            Color3,
        )

    } else {
        listOf(
            ShimmerColor,
            ShimmerColor
        )
    }

    val messageShape = if (isMine) {
        RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 4.dp,
            bottomEnd = 20.dp,
            bottomStart = 20.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 20.dp,
            bottomEnd = 20.dp,
            bottomStart = 20.dp
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {

        if (!isMine) {
            if (userImage.isEmpty()){
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.profile_placeholder),
                    contentDescription = null
                )
            }else{
                AsyncImage(
                    model = ImageRequest.Builder(context).data(userImage).build(),
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Red),
                    contentScale = ContentScale.Crop,
                    contentDescription = "user image"
                )
            }

        }

        Spacer(modifier = Modifier.width(7.dp))
        Column(
            modifier = Modifier
                .padding(
                    end = if (!isMine) 60.dp else 1.dp,
                    top = if (!isMine) 15.dp else 0.dp,
                    start = if (isMine) 60.dp else 1.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .background(brush = Brush.horizontalGradient(messageColor), shape = messageShape)
            ) {
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 13.dp, end = 13.dp, top = 4.dp, bottom = 4.dp),
                    text = message.messageTxt,
                    color = Color.White
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