package com.ahmed.instagramclone.presentation.followers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.presentation.components.SearchCard


@Composable
fun FollowersScreen(
    state: MutableList<User>,
    navigateToUser: (String) -> Unit,
    navigateToUp: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        IconButton(onClick = { navigateToUp() }) {
            Icon(
                modifier = Modifier
                    .size(38.dp)
                    .padding(start = 16.dp, top = 2.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(state) {
                SearchCard(user = it) { navigateToUser(it.userId) }
            }
        }
    }


}