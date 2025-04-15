package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SaveUser(
    private val appRepository: AppRepository,
) {
    operator fun invoke(user: User, shouldRetrieveOldImage: Boolean) = flow {
        val result = appRepository.saveUserInfo(user, shouldRetrieveOldImage)
        emitAll(result)
    }
}