package com.ahmed.instagramclone.data.repository

import android.util.Log
import com.ahmed.instagramclone.domain.model.Message
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.ChatRepository
import com.ahmed.instagramclone.util.Constants.CHAT_REF
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) : ChatRepository {
    override fun sendMessage(receiverId: String, message: String) {
        val ref = database.getReference(CHAT_REF)
        val senderId = auth.currentUser!!.uid
//        val senderId = auth.currentUser!!.uid
        val sendMessage = Message(
            senderId = senderId,
            messageTxt = message
        )


        val msgKey = ref.push().key!!


        val chatId = if (senderId < receiverId) {
            "${senderId}_${receiverId}"
        } else {
            "${receiverId}_${senderId}"
        }


        ref.child(chatId).child(msgKey).setValue(sendMessage)
            .addOnSuccessListener {
                Log.v("CHAT", "chat send successfully")
            }.addOnFailureListener {
                Log.v("CHAT", "is failed")

                it.message?.let { msg ->
                    Log.v("CHAT", msg)
                }
            }


        // Add the sender to receiver chat list

        ref.child("UserChats").child(senderId).child(receiverId).setValue(message)
            .addOnSuccessListener {
                Log.v("CHAT", "chat send successfully")
            }.addOnFailureListener {
                Log.v("CHAT", "is failed")

                it.message?.let { msg ->
                    Log.v("CHAT", msg)
                }
            }
    }

    override fun getMessages(senderId: String) = callbackFlow {
        trySend(Resource.Loading())
        try {

            val receiverId = auth.currentUser!!.uid


            val chatId = if (receiverId > senderId) {
                "${senderId}_${receiverId}"
            } else {
                "${receiverId}_${senderId}"
            }

            val ref = database.getReference(CHAT_REF).child(chatId)

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

    override fun getSenders() = flow {
        emit(Resource.Loading())
        val list = mutableListOf<User>()
        try {


            val reference =
                database.getReference(CHAT_REF).child("UserChats").child(auth.currentUser!!.uid)
                    .get()
                    .await()

            if (reference.exists()) {
                reference.children.forEach {
                    val user = db.collection("Instagram_user").document(it.key!!).get().await()
                        .toObject(User::class.java)
                    user?.let {
                        list.add(user)
                    }
                }
            }

            Log.v("TOOL", list.size.toString())

            emit(Resource.Success(list.toList()))

        } catch (e: Exception) {
            Log.v("TOOL", "Failed to fetch data: ${e.message}")
            emit(Resource.Error(e.message.toString()))
        }
    }
}