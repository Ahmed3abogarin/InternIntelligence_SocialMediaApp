package com.ahmed.instagramclone.domain.model


data class Message(
    val senderId: String = "",
    val isMine: Boolean = false,
    val messageTxt: String = "",
    val time: Long = System.currentTimeMillis()
)
