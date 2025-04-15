package com.ahmed.instagramclone.presentation.comments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.domain.usecases.posts_usecases.PostUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _state = mutableStateOf<Resource<List<CommentWithUser>>?>(null)
    val state = _state


    fun onEvent(event: CommentEvent) {
        when (event) {
            is CommentEvent.AddComment -> {
                addComment(postId = event.postId, commentTxt = event.msg)
            }
            is CommentEvent.GetComments -> {
                getComments(postId = event.postId)
            }
        }
    }

    fun addComment(postId: String, commentTxt: String) {
        viewModelScope.launch {
            postUseCases.addComment(postId = postId, commentTxt = commentTxt).collect {

            }
        }

    }

    fun getComments(postId: String) {
        viewModelScope.launch {
            postUseCases.getComments(postId).collect {
                _state.value = it
            }
        }
    }
}