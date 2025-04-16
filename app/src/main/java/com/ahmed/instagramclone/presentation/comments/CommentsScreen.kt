package com.ahmed.instagramclone.presentation.comments

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.presentation.components.CommentCard
import com.ahmed.instagramclone.ui.theme.SendColor
import com.ahmed.instagramclone.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    postId: String,
    commentsViewModel: CommentsViewModel,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    state: Resource<List<CommentWithUser>>?,
) {
    var commentTxt by remember { mutableStateOf("") }

    LaunchedEffect(false) {
        commentsViewModel.getComments(postId)
    }

    val emojis = listOf(
        "❤\uFE0F",
        "\uD83D\uDE4C",
        "\uD83D\uDD25",
        "\uD83D\uDC4F",
        "\uD83D\uDE22",
        "\uD83D\uDE0D",
        "\uD83D\uDE2F",
        "\uD83D\uDE02"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .height(500.dp)
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Comments",
                fontWeight = FontWeight.Bold
            )

            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is Resource.Success -> {
                        Log.v("Comment", "Comment is success")

                        state.data?.let {
                            if (it.isEmpty()){
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    Text("Start conversation", style = MaterialTheme.typography.headlineMedium)
                                }
                            }
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                items(it.asReversed()) { comment ->
                                    CommentCard(comment)
                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            CircularProgressIndicator(color = Color.LightGray, strokeWidth = 1.dp)
                        }

                    }
                    is Resource.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(text = state.message.toString())
                        }
                    }
                    else -> Unit
                }

            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                items(emojis) {
                    Text(
                        modifier = Modifier
                            .padding(
                                top = 2.dp,
                                bottom = 2.dp
                            )
                            .weight(1f)
                            .clickable {
                                commentTxt += " $it"
                            }, text = it, fontSize = 22.sp
                    )
                }
            }
            Row {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = commentTxt,
                    placeholder = { Text(text = "What do you think of this?") },
                    onValueChange = { commentTxt = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                IconButton(
                    modifier = Modifier.align(Alignment.Bottom),
                    onClick = {
                        commentsViewModel.addComment(
                            postId,
                            commentTxt = commentTxt
                        )
                        commentTxt = ""
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