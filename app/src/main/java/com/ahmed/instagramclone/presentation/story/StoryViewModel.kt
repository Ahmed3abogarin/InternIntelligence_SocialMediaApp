package com.ahmed.instagramclone.presentation.story

import android.net.Uri
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
class StoryViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Unit>?>(null)
    val state = _state

    fun uploadStory(videoUri: Uri) {
        viewModelScope.launch {
            appUseCases.uploadStory(userId = auth.currentUser!!.uid, videoUri = videoUri).collect{
                _state.value = it
            }
        }

    }
}