package com.ahmed.instagramclone.presentation.user

sealed class UserEvent {
    data object FollowUnfollowUser: UserEvent()
}