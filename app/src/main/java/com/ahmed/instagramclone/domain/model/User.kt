package com.ahmed.instagramclone.domain.model

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val imagePath: String = "",
    val followers: List<String> = listOf(),
    val following: List<String> = listOf(),
//    val chatList: List<ChatData> = listOf()
){
    constructor():this("","","","")
}

