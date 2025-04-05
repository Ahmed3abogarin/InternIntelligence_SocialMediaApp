package com.ahmed.instagramclone.presentation.explore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
) : ViewModel() {

    private val _state = mutableStateOf<Resource<List<PostWithAuthor>>?>(null)
    val state: State<Resource<List<PostWithAuthor>>?> = _state

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            appUseCases.getPosts().collect {
                _state.value = it
            }
        }
    }
}