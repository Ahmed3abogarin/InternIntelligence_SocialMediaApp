package com.ahmed.instagramclone.domain.usecases.posts_usecases

import com.ahmed.instagramclone.domain.repository.PostRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class AddComment(
    private val postRepository: PostRepository
) {
    operator fun invoke(postId: String, commentTxt: String) = flow {
        val result = postRepository.commentPost(postId = postId, comment = commentTxt)
        emitAll(result)
    }
}