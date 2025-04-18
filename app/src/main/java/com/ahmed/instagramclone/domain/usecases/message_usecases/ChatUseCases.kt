package com.ahmed.instagramclone.domain.usecases.message_usecases

data class ChatUseCases (
    val sendMessage: SendMessage,
    val getMessages: GetMessages,
    val getSenders: GetSenders,
    val getLastMessage: GetLastMessage
)