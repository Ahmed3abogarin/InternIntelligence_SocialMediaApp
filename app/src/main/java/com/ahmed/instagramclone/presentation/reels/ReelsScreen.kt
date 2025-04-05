package com.ahmed.instagramclone.presentation.reels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.presentation.components.SearchCard
import com.ahmed.instagramclone.util.Resource

@Composable
fun ReelsScreen(state: Resource<List<ReelWithAuthor>>?) {

    state?.data?.let {
        val reels = state.data
        val pagerState = rememberPagerState(pageCount = { reels.size })
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 12.dp, top = 6.dp),
                text = "Reels",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            VerticalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
            ) {
                val reel = reels[it]
                Box{
                    ReelPlayer(reel.post.videoUrl)
                    Row (modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()){
                        SearchCard(reel.author)
                    }

                }


            }
        }


    }
}