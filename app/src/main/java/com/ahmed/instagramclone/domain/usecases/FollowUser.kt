package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class FollowUser(
    private val appRepository: AppRepository
) {
    operator fun invoke(currentUserId: String, targetUserId: String): Flow<Resource<Unit>> = flow{
        val result = appRepository.followUser(currentUserId = currentUserId , targetUserId = targetUserId)
        emitAll(result)
    }
}