package com.example.timer.presentation.countdown
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.timer.common.Constants
import com.example.timer.data.dto.CountDownState
import org.koin.androidx.compose.koinViewModel

@Composable
fun Countdown(vm: CountdownViewModel = koinViewModel()) {
    val state by vm.countdown.collectAsState()

    val btn1Text = when (state.currentState) {
        CountDownState.RUNNING -> "Pause"
        CountDownState.PAUSED -> "Resume"
        else -> "Start"
    }

    val animatedProgress = animateFloatAsState(
        targetValue = (state.currentMillis / Constants.defaultCountdownMillis.toFloat()),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    ).value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .aspectRatio(1f)
        ) {
            CircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                strokeWidth = 20.dp,
                color = Color.Red
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Current Timer")
                Text(
                    "${state.currentMillis / 1000} : ${state.currentMillis % 1000}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp, top = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    when (state.currentState) {
                        CountDownState.INIT -> vm.startCountdown()
                        CountDownState.RUNNING -> vm.pauseCountdown()
                        CountDownState.PAUSED -> vm.resumeCountdown()
                    }
                }
            ) {
                Text(text = btn1Text)
            }
            Button(
                onClick = { vm.cancelCountdown() }
            ) {
                Text("Stop")
            }
        }
    }
}