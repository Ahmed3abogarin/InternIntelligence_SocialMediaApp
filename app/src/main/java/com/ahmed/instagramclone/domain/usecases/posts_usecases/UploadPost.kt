package com.ahmed.instagramclone.domain.usecases.posts_usecases

import com.ahmed.instagramclone.domain.repository.PostRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

class UploadPost(
    private val postRepository: PostRepository
) {
    operator fun invoke(id: String, description: String, byteArray: ByteArray): Flow<Resource<Unit>>{
        return postRepository.uploadPost(id,description,byteArray)
    }
}