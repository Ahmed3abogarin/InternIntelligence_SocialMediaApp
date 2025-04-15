package com.ahmed.instagramclone.presentation.edit

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.RegisterValidation
import com.ahmed.instagramclone.util.Resource
import com.ahmed.instagramclone.util.getImageByteArray
import com.ahmed.instagramclone.util.validateEmail
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val appUseCases: AppUseCases,
    private val application: Application,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<User?>?>(null)
    val state = _state

    private val _updateState = mutableStateOf<Resource<Unit>?>(null)
    val updateState = _updateState

    init {
        getUser()
    }

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.UpdateUserInfo -> {
                updateUser(user = event.user, image = event.uri)
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            appUseCases.getUser(auth.uid!!).collect {
                _state.value = it
            }
        }
    }

    private fun updateUser(user: User, image: Uri?) {
        // validate the input

        val areValid = validateEmail(user.email) is RegisterValidation.Success &&
                user.firstName.trim().isNotEmpty() && user.lastName.trim().isNotEmpty() &&
                user.bio.trim().isNotEmpty()

        if (!areValid){
            _updateState.value = Resource.Error("Input invalid")
            return
        }

        viewModelScope.launch {
            if (image == null) {
                appUseCases.saveUser(user, true).collect {
                    _updateState.value = it
                }
            } else {

                val byteArray = getImageByteArray(context = application, image)
                appUseCases.saveUserWithNewImage(user, byteArray).collect {
                    _updateState.value = it
                }
            }
        }
    }
}