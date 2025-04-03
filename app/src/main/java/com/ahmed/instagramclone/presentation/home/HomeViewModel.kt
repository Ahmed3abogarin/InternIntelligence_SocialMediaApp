package com.ahmed.instagramclone.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
): ViewModel() {
    private val _state = mutableStateOf<Resource<List<PostWithAuthor>>?>(null)
    val state: State<Resource<List<PostWithAuthor>>?> = _state

    init {
        getPosts()
    }

    private fun getPosts(){
        viewModelScope.launch {
            appRepository.getPosts().collect{
                _state.value = it
            }
        }
    }
}