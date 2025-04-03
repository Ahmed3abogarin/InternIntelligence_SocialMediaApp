package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

class UploadPost(
    private val appRepository: AppRepository
) {
    operator fun invoke(id: String, description: String, byteArray: ByteArray): Flow<Resource<Unit>>{
        return appRepository.uploadPost(id,description,byteArray)
    }
}