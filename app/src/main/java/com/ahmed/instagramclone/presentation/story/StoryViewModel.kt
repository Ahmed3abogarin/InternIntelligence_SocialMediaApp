package com.ahmed.instagramclone.presentation.story

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val appRepository: AppRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = mutableStateOf<Resource<List<Story>>?>(null)
    val state = _state

    init {
        savedStateHandle.get<String>("user_id")?.let {
            getStory(it)
        }
    }

    private fun getStory(userId: String){
        Log.v("GETSTORY","GET STORY callled")
        viewModelScope.launch {
            appRepository.getUserStory(userId).collect{
                _state.value = it
            }
        }
    }
}