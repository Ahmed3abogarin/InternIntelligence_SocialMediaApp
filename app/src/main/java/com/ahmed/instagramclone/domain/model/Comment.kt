package com.ahmed.instagramclone.domain.model

data class Comment(
    val userId: String = "",
    val commentTxt: String = "",
    val time: Long = System.currentTimeMillis()
)

data class CommentWithUser(
    val comment: Comment,
    val user: User
)