package com.ahmed.instagramclone.presentation.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.presentation.components.AppSearchBar
import com.ahmed.instagramclone.util.Resource

@Composable
fun ExploreScreen(
    state: Resource<List<PostWithAuthor>>?,
    navigateToSearch: () -> Unit,
    navigateToPostDetails: (PostWithAuthor) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 6.dp)
            .background(Color.White),
    )
    {
        AppSearchBar(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = "",
            readOnly = true,
            onClick = { navigateToSearch() },
            onValueChange = {},
            onSearch = {}
        )
        Spacer(modifier = Modifier.height(6.dp))

        state?.data?.let {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalItemSpacing = 1.dp,
                columns = StaggeredGridCells.Fixed(3),
            ) {
                items(it) { post ->
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(post.post.image).build(),
                        modifier = Modifier
                            .height(110.dp)
                            .width(100.dp)
                            .clickable { navigateToPostDetails(post) },
                        contentScale = ContentScale.Crop,
                        contentDescription = "post small image"
                    )
                }
            }
        }
    }
}