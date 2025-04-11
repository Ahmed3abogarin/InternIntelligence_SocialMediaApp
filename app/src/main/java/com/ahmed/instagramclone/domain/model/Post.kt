package com.ahmed.instagramclone.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize


@Parcelize
data class Post(
    val id: String = "",
    val authorId: String = "",
    val image: String = "",
    val description: String = "",
    val viewCount: Int = 0,
    val likes: List<String> = listOf(),
    val timestamp: Timestamp = Timestamp.now()
): Parcelable

@Parcelize
data class PostWithAuthor(
    val post: Post,
    val author: User
): Parcelable
data class ReelWithAuthor(
    val post: Reel,
    val author: User
)

@Parcelize
data class StoryWithAuthor(
    val story: Story,
    val author: User
): Parcelable