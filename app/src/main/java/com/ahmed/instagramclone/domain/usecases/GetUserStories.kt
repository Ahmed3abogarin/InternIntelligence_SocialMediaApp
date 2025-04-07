package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetUserStories(
    private val appRepository: AppRepository
) {
    operator fun invoke() = flow {
        val result = appRepository.getUserStories()
        emitAll(result)
    }
}