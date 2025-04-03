package com.ahmed.instagramclone.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val appUseCases: AppUseCases
): ViewModel() {

    private val _state = mutableStateOf<Resource<User?>?>(null)
    val state = _state


    init {
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch {
            appUseCases.getUser(auth.currentUser!!.uid).collect{
                _state.value = it
            }
        }

    }
}