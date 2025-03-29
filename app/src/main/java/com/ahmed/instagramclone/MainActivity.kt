package com.ahmed.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.ahmed.instagramclone.presentation.navgraph.NavGraph
import com.ahmed.instagramclone.presentation.navgraph.Route
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramCloneTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    NavGraph(Route.AppStartNavigation.route)
                }
            }
        }
    }
}

