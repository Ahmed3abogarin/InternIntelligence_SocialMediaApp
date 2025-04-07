package com.ahmed.instagramclone.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appUseCases: AppUseCases
): ViewModel() {
    private val _state = mutableStateOf<Resource<List<PostWithAuthor>>?>(null)
    val state: State<Resource<List<PostWithAuthor>>?> = _state

    private val _stories = mutableStateOf<Resource<MutableList<List<StoryWithAuthor>>>?>(null)
    val stories = _stories

    init {
        getPosts()
        getStory()
    }

    private fun getPosts(){
        viewModelScope.launch {
            appUseCases.getPosts().collect{
                _state.value = it
            }
        }
    }

    private fun getStory(){
        viewModelScope.launch {
            appUseCases.getUserStories().collect{
                _stories.value = it
            }
        }

    }
}