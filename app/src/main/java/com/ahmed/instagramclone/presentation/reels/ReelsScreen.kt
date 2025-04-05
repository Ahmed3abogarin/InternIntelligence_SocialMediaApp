package com.ahmed.instagramclone.presentation.reels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ahmed.instagramclone.domain.model.Reel
import com.ahmed.instagramclone.presentation.components.ReelPlayer
import com.ahmed.instagramclone.util.Resource

@Composable
fun ReelsScreen(state: Resource<List<Reel>>?) {

    state?.data?.let {
        val reels = state.data
        val pagerState = rememberPagerState(pageCount = { reels.size })
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) {
            val reel = reels[it]
            ReelPlayer(reel.videoUrl)
        }

    }
}