package com.ahmed.instagramclone.presentation.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.PostWithAuthor

@Composable
fun PostCard(post: PostWithAuthor) {
    val context = LocalContext.current
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
                    Text(text = post.author.firstName +" "+ post.author.lastName  , fontSize = 13.sp)
                    Text(text = post.author.bio, fontSize = 11.sp)
                }
            }


            Icon(Icons.Default.MoreVert, contentDescription = null)
        }

        AsyncImage(
            model = ImageRequest.Builder(context).data(post.post.image).build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(410.dp)
                .padding(top = 8.dp),
            contentScale = ContentScale.FillBounds,
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
                Text(text = post.post.likes.size.toString(), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = null
                )
                Text(text = "121", fontSize = 12.sp)
                Icon(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "app logo"
                )
                Text(text = "24", fontSize = 12.sp)
            }



            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = null
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(modifier = Modifier.padding(horizontal = 10.dp), text = post.post.description, fontSize = 13.sp)
    }
}


@Composable
fun PostsList(posts: List<PostWithAuthor>){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.app_logo),
                    contentDescription = "app logo",
                    modifier = Modifier.width(160.dp)
                )

                Row {
                    Icon(
                        modifier = Modifier.size(34.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "app logo"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = "app logo"
                    )
                }


            }
            Spacer(modifier = Modifier.height(12.dp))
            StoryList()
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(posts){
            PostCard(it)
        }

    }
}