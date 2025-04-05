package com.ahmed.instagramclone.presentation.home

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.presentation.components.PostsList
import com.ahmed.instagramclone.presentation.components.StoryList
import com.ahmed.instagramclone.util.Resource

@Composable
fun HomeScreen(state: Resource<List<PostWithAuthor>>?) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
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

        when (state) {
            is Resource.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                state.data?.let {
                    PostsList(state.data)
                }

            }

            is Resource.Error -> {
                Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }


    }
}
