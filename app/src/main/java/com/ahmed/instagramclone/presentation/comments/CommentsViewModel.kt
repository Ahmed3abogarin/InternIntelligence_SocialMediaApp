package com.ahmed.instagramclone.presentation.comments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<List<CommentWithUser>>?>(null)
    val state = _state

    fun addComment(postId: String, commentTxt: String) {
        viewModelScope.launch {
            appUseCases.addComment(postId = postId, commentTxt = commentTxt).collect {

            }
        }

    }

    fun getComments(postId: String) {
        viewModelScope.launch {
            appUseCases.getComments(postId).collect {
                _state.value = it
            }
        }
    }
}