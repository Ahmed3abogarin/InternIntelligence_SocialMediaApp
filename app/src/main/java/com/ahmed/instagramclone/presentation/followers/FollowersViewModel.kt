package com.ahmed.instagramclone.presentation.followers

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateListOf<User>()
    val state = _state

    init {
        savedStateHandle.get<String>("ids")?.let {
            val list = it.split(",")
            getFollowersFollowing(list)
        }
    }

    private fun getFollowersFollowing(ids: List<String>) {
        viewModelScope.launch {
            ids.forEach {
                appUseCases.getUser(it).collect { state ->
                    when (state) {
                        is Resource.Success -> {
                            state.data?.let { user ->
                                _state.add(user)
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}