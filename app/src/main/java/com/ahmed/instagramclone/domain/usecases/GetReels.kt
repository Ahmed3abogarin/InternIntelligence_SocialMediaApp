package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.Reel
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

class GetReels(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<Resource<List<Reel>>> {
        return appRepository.getReels()
    }
}