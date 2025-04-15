package com.ahmed.instagramclone.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.domain.usecases.posts_usecases.PostUseCases
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val appUseCases: AppUseCases,
    private val postUseCases: PostUseCases
): ViewModel() {

    private val _state = mutableStateOf<Resource<User?>?>(null)
    val state = _state

    private val _postsState = mutableStateOf<Resource<List<PostWithAuthor>>?>(null)
    val postsState = _postsState


    init {
        getUser()
        getPosts()
    }

    private fun getUser(){
        viewModelScope.launch {
            appUseCases.getUser(auth.currentUser!!.uid).collect{
                _state.value = it
            }
        }
    }

    private fun getPosts(){
        viewModelScope.launch {
            postUseCases.getUserPosts(auth.currentUser!!.uid).collect{
                _postsState.value = it
            }
        }
    }
}