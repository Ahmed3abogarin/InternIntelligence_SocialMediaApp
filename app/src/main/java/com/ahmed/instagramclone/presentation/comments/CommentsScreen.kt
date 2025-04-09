package com.ahmed.instagramclone.presentation.comments

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.CommentWithUser
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

    LaunchedEffect (false){
        commentsViewModel.getComments(postId)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.height(500.dp)){

            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is Resource.Success -> {
                        Log.v("Comment", "Comment is success")

                        state.data?.let {
                            LazyColumn(modifier = Modifier.fillMaxSize()){
                                items(it) {
                                    Text(text = it.user.firstName)
                                    Text(text = it.comment.commentTxt)
                                }
                            }
                        }

                    }

                    is Resource.Loading -> {
                        Log.v("Comment", "Comment is loading")

                    }

                    is Resource.Error -> {
                        Log.v("Comment", state.message.toString())

                    }

                    else -> Unit
                }

            }
            Row {
                TextField(
                    value = commentTxt,
                    onValueChange = { commentTxt = it },
                )
                Button(onClick = { commentsViewModel.addComment(postId, commentTxt = commentTxt) }) {
                    Text(text = "comment")
                }
            }



        }
    }
}