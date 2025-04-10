package com.ahmed.instagramclone.presentation.messages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Row (verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    modifier = Modifier.size(38.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }

            Text(text = "Messages",
                style = MaterialTheme.typography.headlineLarge)
        }
        when (state) {
            is Resource.Success -> {
                state.data?.let {
                    LazyColumn {
                        items(it) {
                            SearchCard(user = it) {
                                navigateToUser(it)
                            }
                        }
                    }
                }
            }

            is Resource.Loading -> {
                Log.v("TOOL", "messages senders is loading")
            }

            is Resource.Error -> {
                Log.v("TOOL", state.message.toString())
            }

            else -> Unit
        }

    }


}