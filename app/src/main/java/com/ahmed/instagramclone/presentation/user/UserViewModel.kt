package com.ahmed.instagramclone.presentation.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val appUseCases: AppUseCases,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Unit>?>(null)
    val state = _state


    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.FollowUser -> {
                followUser(event.targetUserId)
            }
        }
    }


    private fun followUser(targetUserId: String) {
        viewModelScope.launch {
            appUseCases.followUser(
                targetUserId = targetUserId,
                currentUserId = auth.currentUser!!.uid
            ).collect {
                _state.value = it


            }
        }

    }
}