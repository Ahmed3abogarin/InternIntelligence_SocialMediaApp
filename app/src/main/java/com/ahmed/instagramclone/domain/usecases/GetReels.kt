package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

class GetReels(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<Resource<List<ReelWithAuthor>>> {
        return appRepository.getReels()
    }
}