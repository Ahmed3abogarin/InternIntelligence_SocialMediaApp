package com.ahmed.instagramclone.data.repository

import android.util.Log
import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.domain.repository.ChatRepository
import com.ahmed.instagramclone.util.Constants.CHAT_REF
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) : ChatRepository {
    override fun sendMessage(chatName: String, message: String) {
        val ref = database.getReference(CHAT_REF)
        val senderId = auth.currentUser!!.uid
        val sendMessage = Message(
            senderId = senderId,
            messageTxt = message
        )
        val msgKey = ref.push().key!!

        // send
        ref.child(chatName).child(msgKey).setValue(sendMessage).addOnSuccessListener {
            Log.v("CHAT", "chat send successfully")


        }.addOnFailureListener {
            it.message?.let { msg ->
                Log.v("CHAT", msg)
            }
        }
    }

    override fun getMessages(authorId: String) = callbackFlow {
        trySend(Resource.Loading())
        try {
            val ref = database.getReference(CHAT_REF).child(authorId)

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = mutableListOf<Message>()
                    for (dataSnapshot in snapshot.children) {
                        val message = dataSnapshot.getValue(Message::class.java)
                        message?.let {
                            val msg = it.copy(isMine = it.senderId == auth.currentUser!!.uid)
                            messageList.add(msg)
                        }
                    }
                    trySend(Resource.Success(messageList))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(error.message))
                    close(error.toException())
                }
            }

            // Attach listener
            ref.addValueEventListener(listener)

            // Clean up when flow collector is cancelled
            awaitClose {
                ref.removeEventListener(listener)
            }

        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: "Unknown error"))
            close(e)
        }
    }

}