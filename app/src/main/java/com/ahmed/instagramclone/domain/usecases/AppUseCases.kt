package com.ahmed.instagramclone.domain.usecases

data class AppUseCases(
    val createNewUser: CreateNewUser,
    val signIn: SignIn,
    val searchUser: SearchUser,
    val getPosts: GetPosts
)