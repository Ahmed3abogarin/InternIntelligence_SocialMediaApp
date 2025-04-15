package com.ahmed.instagramclone.presentation.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.presentation.components.MessageCard
import com.ahmed.instagramclone.presentation.components.SearchCard
import com.ahmed.instagramclone.ui.theme.SendColor
import com.ahmed.instagramclone.ui.theme.ShimmerColor
import com.ahmed.instagramclone.util.Resource

@Composable
fun ChatScreen(
    user: User,
    state: Resource<MutableList<Message>>?,
    event: (ChatEvent) -> Unit,
    navigateUp: () -> Unit,
    navigateToUser: (String) -> Unit,
    showToast: () -> Unit,
) {
    var text by remember { mutableStateOf("") }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(false) {
        event(ChatEvent.GetMessages(user.userId))
    }

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
            SearchCard(user = user, modifier = Modifier.weight(1f)) {
                navigateToUser(user.userId)
            }

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .rotate(90f)
                    .clickable { showToast() },
                painter = painterResource(R.drawable.ic_call),
                contentDescription = "call icon"
            )
            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { showToast() },
                painter = painterResource(R.drawable.ic_video),
                contentDescription = "call icon"
            )

            Spacer(modifier = Modifier.width(16.dp))

        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (state) {
                is Resource.Error -> {
                    Log.v("CHAT", state.message!!)


                }

                is Resource.Success -> {
                    Log.v("CHAT", "chat is successed")
                    Log.v("CHAT", state.data?.size.toString())

                    if (state.data == null) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Chat with ${user.firstName} ${user.lastName}")
                        }
                    }

                    state.data?.let {
                        LaunchedEffect(it.size) {
                            lazyListState.animateScrollToItem(it.lastIndex)

                        }
                        if (it.isEmpty()) {

                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "Chat with ${user.firstName} ${user.lastName}"
                            )

                        }
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 8.dp),
                            reverseLayout = true
                        ) {
                            items(it.asReversed()) { message ->
                                MessageCard(
                                    message = message,
                                    userImage = user.imagePath,
                                    isMine = message.isMine
                                )
                            }
                        }

                    }
                }

                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.LightGray,
                        strokeWidth = 1.5.dp
                    )

                }

                else -> Unit
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .padding(bottom = 4.dp)
                .clip(CircleShape)
                .background(ShimmerColor)
        ) {
            Row {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text(text = "Message...") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        event(ChatEvent.SendMessage(user.userId, message = text))
                        text = ""
                    },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = SendColor)
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(R.drawable.ic_send),
                        tint = Color.White,
                        contentDescription = "app logo"
                    )
                }
            }
        }
    }
}


