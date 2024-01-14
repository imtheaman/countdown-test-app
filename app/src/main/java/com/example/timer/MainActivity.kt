package com.example.timer

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.timer.presentation.countdown.Countdown
import com.example.timer.ui.theme.TimerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App() {
    val requestPermissionState = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    )

    if (requestPermissionState.status.isGranted) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Countdown()
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val textToShow = if (requestPermissionState.status.shouldShowRationale) {
                "Notification is important. Please grant the permission."
            } else {
                "Notification permission required. Please grant the permission."
            }
            Text(textToShow)
            Button(onClick = { requestPermissionState.launchPermissionRequest() }) {
                Text("Allow Permission")
            }
        }
    }
}