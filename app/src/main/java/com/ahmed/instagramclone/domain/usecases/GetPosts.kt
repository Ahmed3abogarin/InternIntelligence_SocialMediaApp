package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetPosts(
    private val appRepository: AppRepository
) {
    operator fun invoke()= flow {
        val result =  appRepository.getPosts()
        emitAll(result)
    }
}