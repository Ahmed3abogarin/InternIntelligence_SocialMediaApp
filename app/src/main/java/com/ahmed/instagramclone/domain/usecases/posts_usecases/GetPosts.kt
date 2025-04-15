package com.ahmed.instagramclone.domain.usecases.posts_usecases

import com.ahmed.instagramclone.domain.repository.PostRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetPosts(
    private val postRepository: PostRepository
) {
    operator fun invoke()= flow {
        val result =  postRepository.getPosts()
        emitAll(result)
    }
}