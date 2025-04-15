package com.ahmed.instagramclone.domain.usecases

data class AppUseCases(
    val searchUser: SearchUser,
    val getUser: GetUser,
    val getReels: GetReels,
    val followUser: FollowUser,
    val unfollowUser: UnfollowUser,
    val uploadStory: UploadStory,
    val getStory: GetStory,
    val getUserStories: GetUserStories,
    val saveUser: SaveUser,
    val saveUserWithNewImage: SaveUserWithNewImage
)