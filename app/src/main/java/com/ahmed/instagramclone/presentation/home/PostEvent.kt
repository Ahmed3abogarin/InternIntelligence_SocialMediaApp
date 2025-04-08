package com.ahmed.instagramclone.presentation.home

sealed class PostEvent {
    data class LikeUnlikePost(val postId: String): PostEvent()
}