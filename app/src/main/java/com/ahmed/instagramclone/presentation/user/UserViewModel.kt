package com.ahmed.instagramclone.presentation.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val appUseCases: AppUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<User?>?>(null)
    val state = _state

    private val _postsState = mutableStateOf<Resource<List<PostWithAuthor>>?>(null)
    val postsState = _postsState

    private val _isFollowing = mutableStateOf(false)
    val isFollowing = _isFollowing

    init {
        savedStateHandle.get<String>("user_id")?.let {
            getUser(it)
            getUserPosts(it)
        }
    }

    private fun checkStatus(): Boolean{
        state.value?.data?.let {
            val user = it
            return user.followers.contains(auth.currentUser!!.uid)
        }
        return false
    }


    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.FollowUnfollowUser -> {
                val user = state.value!!.data!!
                if (checkStatus()){
                    unfollowUser(user.userId)
                }else{
                    // unfollow with same button
                    followUser(user.userId)
                }
            }
        }
    }

    private fun getUserPosts(userId: String){
        viewModelScope.launch {
            appUseCases.getUserPosts(userId).collectLatest {
                _postsState.value = it
            }
        }
    }

    private fun getUser(userId: String) {
        viewModelScope.launch {
            appUseCases.getUser(userId).collectLatest {
                _state.value = it
                _isFollowing.value = checkStatus()

            }
        }
    }

    private fun unfollowUser(targetUserId: String) {
        Log.v("FOLLOWERSFROMVM","Unfollow called")


        viewModelScope.launch {
            appUseCases.unfollowUser(
                currentUserId = auth.currentUser!!.uid,
                targetUserId = targetUserId
            ).collect{
                when(it){
                    is Resource.Success ->{
                        Log.v("FOLLOWERSFROMVM","Unfollow successfully")
                    }
                    is Resource.Error -> {
                        Log.v("FOLLOWERSFROMVM","error !!!!!!")
                    }
                    else -> Unit
                }


            }
        }
    }


    private fun followUser(targetUserId: String) {
        viewModelScope.launch {
                appUseCases.followUser(
                    targetUserId = targetUserId,
                    currentUserId = auth.currentUser!!.uid
                ).collect{
                    when(it){
                        is Resource.Success ->{
                            Log.v("FOLLOWERSFROMVM",_state.value!!.data!!.followers.size.toString())
                        }
                        else -> Unit
                    }


                }

        }
    }
}