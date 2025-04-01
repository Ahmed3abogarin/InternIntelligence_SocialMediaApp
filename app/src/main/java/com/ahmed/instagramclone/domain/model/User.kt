package com.ahmed.instagramclone.domain.model

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val imagePath: String = ""
)
