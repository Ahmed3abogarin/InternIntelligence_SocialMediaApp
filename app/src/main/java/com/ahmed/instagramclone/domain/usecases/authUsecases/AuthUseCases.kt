package com.ahmed.instagramclone.domain.usecases.authUsecases

data class AuthUseCases(
    val createNewUser: Register,
    val signIn: SignIn,
)