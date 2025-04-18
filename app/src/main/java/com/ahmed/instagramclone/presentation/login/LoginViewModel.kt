package com.ahmed.instagramclone.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.util.Resource
import com.ahmed.instagramclone.domain.usecases.authUsecases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Unit>?>(null)
    val state: State<Resource<Unit>?> = _state



    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                signInUser(event.email, event.password)
            }
        }
    }

    private fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.signIn(email = email, password = password).collect {
                _state.value = it
            }
        }

    }
}