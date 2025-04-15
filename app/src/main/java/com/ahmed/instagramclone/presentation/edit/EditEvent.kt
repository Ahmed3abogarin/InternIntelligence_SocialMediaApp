package com.ahmed.instagramclone.presentation.edit

import android.net.Uri
import com.ahmed.instagramclone.domain.model.User

sealed class EditEvent {
    data class UpdateUserInfo(val user: User,val uri: Uri?): EditEvent()
}