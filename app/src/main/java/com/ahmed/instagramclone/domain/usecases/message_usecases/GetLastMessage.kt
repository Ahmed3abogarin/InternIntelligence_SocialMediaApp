package com.ahmed.instagramclone.domain.usecases.message_usecases

import com.ahmed.instagramclone.domain.repository.ChatRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetLastMessage(
    private val chatRepository: ChatRepository,
) {
    operator fun invoke(senderId: String) = flow {
        val result = chatRepository.getLastMessage(senderId)
        emitAll(result)
    }
}