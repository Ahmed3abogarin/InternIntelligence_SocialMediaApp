package com.ahmed.instagramclone

sealed class RegisterValidation {
    data object Success : RegisterValidation()
    data class Failed(val message:String):RegisterValidation()
}
data class RegisterFieldsState(
    val email : RegisterValidation,
    val password: RegisterValidation
)