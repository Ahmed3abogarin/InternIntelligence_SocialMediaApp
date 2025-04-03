package com.ahmed.instagramclone.presentation.search

import com.ahmed.instagramclone.domain.model.User

data class SearchState(
    val searchQuery: String = "",
    val searchedUsers: List<User>? = null
)