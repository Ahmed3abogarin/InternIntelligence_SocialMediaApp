package com.ahmed.instagramclone.presentation.messages

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.message_usecases.ChatUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<List<User>>?>(null)
    val state = _state

    init {
        getMessages()
    }

    private fun getMessages() {
        viewModelScope.launch {
            chatUseCases.getSenders().collect {
                _state.value = it
            }
        }
    }
}