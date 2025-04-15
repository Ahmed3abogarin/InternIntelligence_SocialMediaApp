package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SaveUserWithNewImage(
    private val appRepository: AppRepository,
) {
    operator fun invoke(user: User, byteArray: ByteArray) = flow {
        val result = appRepository.saveUserInfoWithNewImage(user, byteArray)
        emitAll(result)
    }
}