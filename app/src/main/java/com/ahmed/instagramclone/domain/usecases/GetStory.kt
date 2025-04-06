package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetStory(
    private val appRepository: AppRepository,
) {
    operator fun invoke(userId: String) = flow {
        val result = appRepository.getUserStory(userId)
        emitAll(result)
    }
}