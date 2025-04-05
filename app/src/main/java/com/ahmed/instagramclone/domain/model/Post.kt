package com.ahmed.instagramclone.domain.model

import com.google.firebase.Timestamp
import java.util.UUID

data class Post(
    val id: String = UUID.randomUUID().toString(),
    val authorId: String = "",
    val image: String = "",
    val description: String = "",
    val viewCount: Int = 0,
    val likes: List<String> = listOf(),
    val timestamp: Timestamp = Timestamp.now()
)

data class PostWithAuthor(
    val post: Post,
    val author: User
)
data class ReelWithAuthor(
    val post: Reel,
    val author: User
)