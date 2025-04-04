package com.ahmed.instagramclone.presentation.reels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.Reel
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReelsViewModel @Inject constructor(
    private val appUseCases: AppUseCases
):ViewModel() {

    private val _state = mutableStateOf<Resource<List<Reel>>?>(null)
    val state = _state

    init {
        getReels()
    }

    private fun getReels(){
        viewModelScope.launch {
            appUseCases.getReels().collect{
                _state.value = it
            }
        }


    }
}