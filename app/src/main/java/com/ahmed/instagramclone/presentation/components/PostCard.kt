package com.ahmed.instagramclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.instagramclone.R

@Composable
fun PostCard() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Cyan)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "Account name", fontSize = 13.sp)
                    Text(text = "Sponsored", fontSize = 11.sp)
                }
            }


            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(410.dp)
                .padding(top = 8.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(34.dp),
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
                Text(text = "4,567")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = null
                )
                Text(text = "121")
                Icon(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "app logo"
                )
                Text(text = "121")
            }



            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = null
            )

        }
    }
}