package com.ahmed.instagramclone.presentation.loginregister

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.Resource
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appUseCases: AppUseCases
):ViewModel() {

    private val _state = mutableStateOf<Resource<Unit>?>(null)
    val state: State<Resource<Unit>?> = _state

    private val _registerState = MutableStateFlow<Resource<Unit>?>(null)


    fun createNewUser(user: User,password: String){
        viewModelScope.launch {
            appUseCases.createNewUser(user = user, password = password).collect{
                _state.value = it
            }
        }
    }
}