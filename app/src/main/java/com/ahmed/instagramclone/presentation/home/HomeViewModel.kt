package com.ahmed.instagramclone.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.domain.usecases.posts_usecases.PostUseCases
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val postUseCases: PostUseCases,
    private val appUseCases: AppUseCases
) : ViewModel() {
    private val _state = mutableStateOf<Resource<List<PostWithAuthor>>?>(null)
    val state: State<Resource<List<PostWithAuthor>>?> = _state

    private val _stories = mutableStateOf<Resource<MutableList<List<StoryWithAuthor>>>?>(null)
    val stories = _stories

    val currentUserId = auth.currentUser?.uid


    init {
        getPosts()
        getStory()
    }

    fun onEvent(event: PostEvent) {
        when (event) {
            is PostEvent.LikeUnlikePost -> {
                if (checkStatus(event.postId)){
                    unlikePost(event.postId)
                }else{
                    likePost(event.postId)
                }
            }

        }
    }

    private fun unlikePost(postId: String) {
        viewModelScope.launch {
            postUseCases.unlikePost(postId).collect {

            }
        }
    }

    private fun likePost(postId: String) {
        viewModelScope.launch {
            postUseCases.likePost(postId).collect {

            }
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            postUseCases.getPosts().collect {
                _state.value = it
            }
        }
    }

    private fun getStory() {
        viewModelScope.launch {
            appUseCases.getUserStories().collect {
                _stories.value = it
            }
        }

    }

    private fun checkStatus(postId: String): Boolean {
        val list = state.value?.data?.find { it.post.id == postId }
        list?.post?.likes?.let {
            val status = it.contains(auth.currentUser!!.uid)
            return status
        }
        return false
    }
}