package com.ahmed.instagramclone.domain.repository

import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(chatName: String,message: String)
    fun getMessages(authorId: String): Flow<Resource<MutableList<Message>>>
}