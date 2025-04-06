package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetUserPosts(
    private val appRepository: AppRepository,
) {
    operator fun invoke(userId: String) = flow {
        val result = appRepository.getUserPosts(userId)
        emitAll(result)
    }
}