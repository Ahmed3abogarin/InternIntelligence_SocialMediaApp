package com.ahmed.instagramclone.domain.model

import com.google.firebase.Timestamp
import java.util.UUID

data class Reel(
    val id: String = UUID.randomUUID().toString(),
    val authorId: String = "",
    val videoUrl: String = "",
    val description: String = "",
    val viewCount: Int = 0,
    val likes: List<String> = listOf(),
    val timestamp: Timestamp = Timestamp.now()
)
