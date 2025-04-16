package com.ahmed.instagramclone.presentation.user

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.ProfileTabs
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.ui.theme.SendColor
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    state: Resource<User?>?,
    posts: Resource<List<PostWithAuthor>>?,
    navigateToUp: () -> Unit,
    isFollowing: MutableState<Boolean>,
    event: (UserEvent) -> Unit,
    navigateToUserStory: (String) -> Unit,
    navigateToChat: (User) -> Unit,
    navigateToFollowers: (List<String>, String) -> Unit,
    navigateToDetails: (PostWithAuthor) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ProfileTabs.entries.size })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }
    val context = LocalContext.current
    val colors = listOf(Color.Magenta, Color.Cyan, Color.Yellow)


    when (state) {
        is Resource.Success -> {
            state.data?.let {
                val user = it
                var followers by remember { mutableIntStateOf(user.followers.size) }

                val updatedFollowers = user.followers.size
                if (followers != updatedFollowers) {
                    followers = updatedFollowers
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        IconButton(onClick = { navigateToUp() }) {
                            Icon(
                                modifier = Modifier.size(38.dp),
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }

                        Text(
                            text = user.firstName + " " + user.lastName,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                        )

                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    Row {
                        Box(contentAlignment = Alignment.Center) {
                            if (user.hasStory) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(96.dp)
                                        .background(Brush.radialGradient(colors))
                                )
                            }
                            if (user.imagePath.isEmpty()) {
                                Image(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(90.dp)
                                        .background(Color.Black)
                                        .clickable { if (user.hasStory) navigateToUserStory(user.userId) },
                                    painter = painterResource(R.drawable.profile_placeholder),
                                    contentDescription = "user profile photo"
                                )
                            } else {
                                AsyncImage(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(90.dp)
                                        .background(Color.Black)
                                        .clickable { if (user.hasStory) navigateToUserStory(user.userId) },
                                    model = ImageRequest.Builder(context).data(context)
                                        .data(user.imagePath)
                                        .build(),
                                    contentDescription = "user profile photo",
                                    contentScale = ContentScale.Crop
                                )
                            }

                        }


                        Spacer(modifier = Modifier.width(22.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "0",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(text = "posts", style = MaterialTheme.typography.titleMedium)
                            }
                            Column(modifier = Modifier.clickable {
                                navigateToFollowers(
                                    user.followers,
                                    "Followers"
                                )
                            }, horizontalAlignment = Alignment.CenterHorizontally) {

                                Text(
                                    text = followers.toString(),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )


                                Text(
                                    text = "followers",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Column(modifier = Modifier.clickable {
                                navigateToFollowers(
                                    user.following,
                                    "Following"
                                )
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = user.following.size.toString(),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    text = "following",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = user.firstName + " " + user.lastName,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = user.bio,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                event(UserEvent.FollowUnfollowUser)
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = SendColor)
                        ) {
                            Text(
                                text = if (isFollowing.value) "Following" else "Follow",
                                color = Color.White
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navigateToChat(it)
                            },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                        ) {
                            Text(text = "Message")
                        }

                        OutlinedButton(
                            modifier = Modifier.weight(0.25f),
                            onClick = {},
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                tint = Color.Black,
                                imageVector = Icons.Default.PersonAddAlt,
                                contentDescription = null
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    TabRow(
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                                    .fillMaxWidth(0.2f)
                                    .background(Color.Red, shape = RoundedCornerShape(50)),
                                color = Color.Red
                            )
                        },
                        selectedTabIndex = selectedTabIndex.value,
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        ProfileTabs.entries.forEachIndexed { index, currentTab ->
                            Tab(
                                selected = selectedTabIndex.value == index,
                                selectedContentColor = Color.Black,
                                unselectedContentColor = Color.Black,
                                onClick = {
                                    scope.launch { pagerState.animateScrollToPage(currentTab.ordinal) }
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(
                                            if (selectedTabIndex.value == index)
                                                currentTab.selectedIcon else currentTab.unselectedIcon
                                        ),
                                        contentDescription = null
                                    )
                                }
                            )

                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (it) {
                                0 -> {
                                    posts?.data?.let { list ->
                                        if (list.isEmpty()) {
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
                                            items(list) { post ->
                                                AsyncImage(
                                                    model = ImageRequest.Builder(context)
                                                        .data(post.post.image)
                                                        .build(),
                                                    modifier = Modifier
                                                        .height(200.dp)
                                                        .width(205.dp)
                                                        .clickable { navigateToDetails(post) },
                                                    contentScale = ContentScale.Fit,
                                                    contentDescription = "user account post image"
                                                )
                                            }
                                        }
                                    }

                                }

                                1 -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "No Reels")
                                    }

                                }

                                2 -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "No Tags")
                                    }
                                }

                                else -> Unit
                            }
                        }

                    }

                }
            }

        }

        is Resource.Error -> {
            Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
        }

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        else -> Unit
    }


}

