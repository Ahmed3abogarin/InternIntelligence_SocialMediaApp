package com.ahmed.instagramclone.presentation.comments

sealed class CommentEvent {
    data class AddComment(val msg: String,val postId: String): CommentEvent()
    data class GetComments(val postId: String): CommentEvent()
}