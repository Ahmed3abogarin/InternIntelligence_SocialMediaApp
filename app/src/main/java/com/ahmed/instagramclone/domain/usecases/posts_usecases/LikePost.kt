package com.ahmed.instagramclone.domain.usecases.posts_usecases

import com.ahmed.instagramclone.domain.repository.PostRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class LikePost(
    private val postRepository: PostRepository
) {
    operator fun invoke(postId: String) = flow {
        val result = postRepository.likePost(postId)
        emitAll(result)
    }
}