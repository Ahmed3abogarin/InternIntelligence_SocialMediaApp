package com.ahmed.instagramclone.presentation.login

sealed class LoginEvent {
    data class Login(val email: String, val password: String): LoginEvent()
}