package com.ahmed.instagramclone.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.domain.model.PostWithAuthor

@Composable
fun UserPostsList(posts: List<PostWithAuthor>, navigateToDetails: (PostWithAuthor) -> Unit) {
    val context = LocalContext.current
    if (posts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No posts",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalItemSpacing = 1.dp,
        columns = StaggeredGridCells.Fixed(3),
    ) {
        items(posts) { post ->
            AsyncImage(
                model = ImageRequest.Builder(context).data(post.post.image)
                    .build(),
                modifier = Modifier
                    .height(200.dp)
                    .width(195.dp)
                    .clickable { navigateToDetails(post) },
                contentScale = ContentScale.Crop,
                contentDescription = "user account post image"
            )
        }
    }
}