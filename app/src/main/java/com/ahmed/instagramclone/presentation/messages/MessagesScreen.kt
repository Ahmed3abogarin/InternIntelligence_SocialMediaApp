package com.ahmed.instagramclone.presentation.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.presentation.components.SearchCard
import com.ahmed.instagramclone.util.Resource

@Composable
fun MessagesScreen(
    state: Resource<List<User>>?,
    navigateToUser: (User) -> Unit,
    navigateUp: () -> Unit,
) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    modifier = Modifier.size(38.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Messages",
                style = MaterialTheme.typography.headlineLarge
            )
        }
        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 8.dp))
        when (state) {
            is Resource.Success -> {
                state.data?.let {
                    LazyColumn(
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(it) {
                            SearchCard(user = it) {
                                navigateToUser(it)
                            }
                        }
                    }
                }
            }

            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.LightGray, strokeWidth = 1.dp)
                }
            }

            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message.toString())
                }
            }

            else -> Unit
        }

    }


}