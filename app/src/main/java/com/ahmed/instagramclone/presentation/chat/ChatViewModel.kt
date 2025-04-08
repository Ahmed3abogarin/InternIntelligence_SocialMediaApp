package com.ahmed.instagramclone.presentation.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.domain.usecases.message_usecases.ChatUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<MutableList<Message>>?>(null)
    val state = _state


    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.GetMessages -> {
                getMessages(event.authorId)
            }

            is ChatEvent.SendMessage -> {
                sendMessage(chatName = event.authorId, message = event.message)
            }
        }
    }


    private fun getMessages(authorId: String) {
        viewModelScope.launch {
            chatUseCases.getMessages(authorId).collect {
                _state.value = it

            }
        }
    }

    private fun sendMessage(chatName: String, message: String) {
        if (message.trim().isNotEmpty()) {
            chatUseCases.sendMessage(chatName = chatName, message = message)
        }

    }
}