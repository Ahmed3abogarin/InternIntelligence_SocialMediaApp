package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class LikePost(
    private val appRepository: AppRepository,
) {
    operator fun invoke(postId: String) = flow {
        val result = appRepository.likePost(postId)
        emitAll(result)
    }
}