package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPosts(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<Resource<List<PostWithAuthor>>> {
        return appRepository.getPosts()
    }
}