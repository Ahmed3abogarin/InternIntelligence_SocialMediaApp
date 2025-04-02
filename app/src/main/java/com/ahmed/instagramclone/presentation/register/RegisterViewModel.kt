package com.ahmed.instagramclone.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.RegisterFieldsState
import com.ahmed.instagramclone.RegisterValidation
import com.ahmed.instagramclone.Resource
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.validateEmail
import com.ahmed.instagramclone.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FieldsState(
    val emailState: String? = null,
    val passwordState: String? = null,
)
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appUseCases: AppUseCases
):ViewModel() {

    private val _state = mutableStateOf<Resource<Unit>?>(null)
    val state: State<Resource<Unit>?> = _state

    private val _registerState = mutableStateOf(FieldsState())
    val registerState = _registerState

    fun createNewUser(user: User,password: String, confirmPassword: String){
        viewModelScope.launch {
            if (checkValidation(user = user, password = password, confirmPassword = confirmPassword)){
                appUseCases.createNewUser(user = user, password = password).collect{
                    _state.value = it
                }
            }else{
                val registerValidState = RegisterFieldsState(
                    validateEmail(user.email),validatePassword(password))
                if (registerValidState.email is RegisterValidation.Failed){
                    _registerState.value = _registerState.value.copy(emailState =registerValidState.email.message)
                }
                if (registerValidState.password is RegisterValidation.Failed){
                    _registerState.value = _registerState.value.copy(passwordState =registerValidState.password.message)
                }
            }
        }
    }

    private fun checkValidation(user: User, password: String, confirmPassword: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)

        val shouldRegister = emailValidation is RegisterValidation.Success
                && passwordValidation is RegisterValidation.Success && password == confirmPassword

        return shouldRegister
    }
}