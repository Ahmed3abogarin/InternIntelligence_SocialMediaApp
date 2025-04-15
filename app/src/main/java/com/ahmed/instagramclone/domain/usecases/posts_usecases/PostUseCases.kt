package com.ahmed.instagramclone.domain.usecases.posts_usecases

data class PostUseCases(
    val getPosts: GetPosts,
    val uploadPost: UploadPost,
    val getUserPosts: GetUserPosts,
    val likePost: LikePost,
    val unlikePost: UnlikePost,
    val addComment: AddComment,
    val getComments: GetComments,
)