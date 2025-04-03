package com.ahmed.instagramclone.domain.model

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val bio: String = "",
    val imagePath: String = "",
    val followers: Int = 0,
    val following: Int = 0,
//    val chatList: List<ChatData> = listOf()
)

