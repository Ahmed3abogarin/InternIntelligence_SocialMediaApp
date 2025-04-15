package com.ahmed.instagramclone.presentation.new_post

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import com.ahmed.instagramclone.util.getImageByteArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val application: Application
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Unit>?>(null)
    val state = _state

    fun onEvent(event: NewPostEvent) {
        when (event) {
            is NewPostEvent.UploadPost -> {
                val byteArray = getImageByteArray(context = application, imageUri = event.imageUri)
                uploadPost(
                    description = event.description,
                    byteArray = byteArray
                )
            }
        }
    }

    private fun uploadPost(description: String, byteArray: ByteArray) {
        viewModelScope.launch {
            appUseCases.uploadPost(
                id = UUID.randomUUID().toString(),
                description = description,
                byteArray = byteArray
            ).collect{
                _state.value = it
            }
        }

    }
}