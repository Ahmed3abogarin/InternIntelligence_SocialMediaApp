package com.ahmed.instagramclone.presentation.mainactivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ahmed.instagramclone.presentation.navgraph.Route
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    auth: FirebaseAuth,
) : ViewModel() {

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)

    init {
        if (auth.currentUser != null) {
            startDestination = Route.AppMainNavigation.route
        }
    }
}