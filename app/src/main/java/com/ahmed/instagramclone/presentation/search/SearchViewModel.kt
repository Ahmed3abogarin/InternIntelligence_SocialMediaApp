package com.ahmed.instagramclone.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
) : ViewModel() {
    private var searchQuery = ""

    private val _state = mutableStateOf<Resource<List<User>>?>(null)
    val state: State<Resource<List<User>>?> = _state


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                searchQuery = event.searchQuery
            }

            is SearchEvent.SearchNews -> {
                searchUsers()
            }
        }
    }

    private fun searchUsers() {
        viewModelScope.launch {
            if (searchQuery.isNotEmpty()){
                appUseCases.searchUser(searchQuery).collect{
                    _state.value = it
                }
            }
        }


    }
}