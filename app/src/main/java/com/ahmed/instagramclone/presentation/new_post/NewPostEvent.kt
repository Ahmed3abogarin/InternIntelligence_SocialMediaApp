package com.ahmed.instagramclone.presentation.new_post

import android.net.Uri

sealed class NewPostEvent {
    data class UploadPost(val imageUri: Uri, val description: String): NewPostEvent()
}