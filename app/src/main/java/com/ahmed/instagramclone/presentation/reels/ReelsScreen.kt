package com.ahmed.instagramclone.presentation.reels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.presentation.components.SearchCard
import com.ahmed.instagramclone.util.Resource

@Composable
fun ReelsScreen(state: Resource<List<ReelWithAuthor>>?, navigateToUser: (User) -> Unit) {

    val isPlaying = remember { mutableStateOf(true) }

    state?.data?.let {
        val reels = state.data
        val pagerState = rememberPagerState(pageCount = { reels.size })

        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) {
            val reel = reels[it]
            Box {
                ReelPlayer(reel.post.videoUrl, isPlaying = isPlaying )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                ) {
                    SearchCard(reel.author, textColor = Color.White, navigateToUser = { navigateToUser(reel.author) })
                }
                Text(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(start = 16.dp, top = 6.dp),
                    text = "Reels",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold, color = Color.White)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 6.dp)
                        .padding(bottom = 18.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(34.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(text = reel.post.likes.size.toString(), fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(text = "121", fontSize = 12.sp, color = Color.White)
                    Icon(
                        modifier = Modifier.size(34.dp),
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = "app logo",
                        tint = Color.White
                    )
                    Text(text = "24", fontSize = 12.sp,color = Color.White)
                    Icon(
                        modifier = Modifier.size(34.dp),
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "app logo",
                        tint = Color.White
                    )
                }
                if (isPlaying.value){
                    IconButton(modifier = Modifier.align(Alignment.Center), onClick = {}) {
                        Icon(
                            modifier = Modifier.size(34.dp),
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "app logo",
                            tint = Color.White.copy(alpha = 0.5f)

                        )
                    }
                }
            }
        }
    }
}