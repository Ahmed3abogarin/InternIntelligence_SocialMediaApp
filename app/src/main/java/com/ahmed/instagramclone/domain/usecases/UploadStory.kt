package com.ahmed.instagramclone.domain.usecases

import android.net.Uri
import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class UploadStory(
    private val appRepository: AppRepository,
) {
    operator fun invoke(userId: String, videoUri: Uri) = flow {
        val result = appRepository.uploadStory(userId = userId, videoUri = videoUri)
        emitAll(result)
    }
}