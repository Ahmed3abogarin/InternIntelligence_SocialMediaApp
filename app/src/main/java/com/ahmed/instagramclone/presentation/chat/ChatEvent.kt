package com.ahmed.instagramclone.presentation.chat

sealed class ChatEvent {
    data class GetMessages(val authorId: String): ChatEvent()
    data class SendMessage(val authorId: String,val message: String): ChatEvent()
}