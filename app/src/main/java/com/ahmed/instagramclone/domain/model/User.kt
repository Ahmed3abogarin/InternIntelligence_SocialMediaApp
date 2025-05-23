package com.ahmed.instagramclone.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val bio: String = "",
    val imagePath: String = "",
    val hasStory: Boolean = false,
    val followers: List<String> = listOf(),
    val following: List<String> = listOf(),
//    val chatList: List<ChatData> = listOf()
) : Parcelable

