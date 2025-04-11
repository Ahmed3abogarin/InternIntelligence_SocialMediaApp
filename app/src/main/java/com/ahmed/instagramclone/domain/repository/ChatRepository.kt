package com.ahmed.instagramclone.domain.repository

import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(receiverId: String,message: String)
    fun getMessages(senderId: String, receiverId: String): Flow<Resource<MutableList<Message>>>

    fun getSenders(): Flow<Resource<List<User>>>
}