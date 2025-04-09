package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class AddComment(
    private val appRepository: AppRepository,
) {
    operator fun invoke(postId: String, commentTxt: String) = flow {
        val result = appRepository.commentPost(postId = postId, comment = commentTxt)
        emitAll(result)
    }
}