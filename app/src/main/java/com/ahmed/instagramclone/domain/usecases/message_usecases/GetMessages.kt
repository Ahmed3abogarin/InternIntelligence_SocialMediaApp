package com.ahmed.instagramclone.domain.usecases.message_usecases

import com.ahmed.instagramclone.domain.repository.ChatRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetMessages(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(authorId: String) = flow{
        val result = chatRepository.getMessages(authorId)
        emitAll(result)
    }
}