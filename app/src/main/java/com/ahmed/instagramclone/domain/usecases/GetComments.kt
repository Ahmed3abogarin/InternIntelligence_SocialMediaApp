package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetComments(
    private val appRepository: AppRepository,
) {
    operator fun invoke(postId: String) = flow {
        val result = appRepository.getComments(postId)
        emitAll(result)
    }
}