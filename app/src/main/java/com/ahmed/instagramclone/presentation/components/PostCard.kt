package com.ahmed.instagramclone.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.presentation.home.PostEvent
import com.ahmed.instagramclone.util.Resource

@Composable
fun PostCard(post: PostWithAuthor, event: (PostEvent) -> Unit, onCommentClicked: (String) -> Unit) {
    val context = LocalContext.current

    var likes by remember { mutableIntStateOf(post.post.likes.size) }

    val updatedLikes = post.post.likes.size
    if (likes != updatedLikes) {
        likes = updatedLikes
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (post.author.imagePath.isEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        painter = painterResource(R.drawable.profile_placeholder),
                        contentDescription = null
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(post.post.image).build(),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = post.author.firstName + " " + post.author.lastName,
                        fontSize = 13.sp
                    )
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
                IconButton(
                    onClick = { event(PostEvent.LikeUnlikePost(post.post.id)) }
                ) {
                    Icon(
                        modifier = Modifier.size(34.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }

                Text(text = likes.toString(), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { onCommentClicked(post.post.id) }) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = null
                    )
                }

//                Text(text = post.post, fontSize = 12.sp)
                Icon(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "app logo"
                )
//                Text(text = "24", fontSize = 12.sp)
            }



            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = null
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = post.post.description,
            fontSize = 13.sp
        )
    }
}


@Composable
fun PostsList(
    state: Resource<List<PostWithAuthor>>?,
    stories: Resource<MutableList<List<StoryWithAuthor>>>?,
    navigateToStory: () -> Unit,
    navigateUserToStory: (StoryWithAuthor) -> Unit,
    onCommentClicked: (String) -> Unit,
    event: (PostEvent) -> Unit,
    navigateToMessages: () -> Unit,
) {
    val context = LocalContext.current
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
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { navigateToMessages() },
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = "app logo"
                    )
                }


            }
            Spacer(modifier = Modifier.height(12.dp))

            when (stories) {
                is Resource.Loading -> {
                    Log.v("STORY", "story is loading")
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    stories.data?.let {
                        StoryList(
                            navigateToAddStory = navigateToStory,
                            stories = stories.data,
                            navigateUserToStory = { story ->
                                navigateUserToStory(story)
                            }
                        )
                    }
                }

                is Resource.Error -> {
                    Log.v("STORY", "story is error")
                    Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show()
                }

                else -> Unit
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
        when (state) {
            is Resource.Loading -> {
                item {
                    ShimmerEffect()
                }

            }

            is Resource.Success -> {
                state.data?.let {
                    val posts = it
                    items(posts) { post ->
                        PostCard(
                            post,
                            event = event,
                            onCommentClicked = { id -> onCommentClicked(id) })
                    }
                }
            }

            is Resource.Error -> {
                Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }
}
