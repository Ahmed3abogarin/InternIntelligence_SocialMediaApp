package com.ahmed.instagramclone.domain.usecases.message_usecases

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.ChatRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetSenders(
    private val chatRepository: ChatRepository,
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        val result = chatRepository.getSenders()
        emitAll(result)
    }
}