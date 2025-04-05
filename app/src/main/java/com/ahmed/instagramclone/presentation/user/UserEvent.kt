package com.ahmed.instagramclone.presentation.user

sealed class UserEvent {
    data class FollowUser(val targetUserId: String): UserEvent()
}