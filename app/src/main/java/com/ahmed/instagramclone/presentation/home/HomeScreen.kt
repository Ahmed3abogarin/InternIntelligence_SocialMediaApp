package com.ahmed.instagramclone.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.presentation.components.PostCard
import com.ahmed.instagramclone.presentation.components.StoryList
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scrollState)
    ) {
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
        PostCard()

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    InstagramCloneTheme {
        HomeScreen()
    }
}