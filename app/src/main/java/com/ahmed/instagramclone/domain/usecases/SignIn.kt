package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.util.Resource
import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SignIn(
    private val appRepository: AppRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Unit>> = flow{
        val result = appRepository.signIn(email = email,password = password)
        emitAll(result)
    }
}