package com.ahmed.instagramclone.presentation.home

sealed class PostEvent {
    data class LikePost(val postId: String): PostEvent()
}