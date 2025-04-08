package com.ahmed.instagramclone.domain.usecases.message_usecases

import com.ahmed.instagramclone.domain.repository.ChatRepository

class SendMessage(
    private val chatRepository: ChatRepository,
) {
    operator fun invoke(chatName: String, message: String) {
        chatRepository.sendMessage(chatName = chatName, message = message)
    }
}