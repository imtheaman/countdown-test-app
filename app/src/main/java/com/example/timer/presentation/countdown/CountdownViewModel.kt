package com.example.timer.presentation.countdown

import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer.common.Constants
import com.example.timer.common.isAppIsInBackground
import com.example.timer.common.notify
import com.example.timer.data.dto.CountDownState
import com.example.timer.data.dto.CountdownDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CountdownViewModel(private val context: Context) : ViewModel() {
    private val myProcess = RunningAppProcessInfo()
    private val _countdown = MutableStateFlow(CountdownDto(CountDownState.INIT, Constants.defaultCountdownMillis))
    val countdown: StateFlow<CountdownDto> = _countdown.asStateFlow()
    private var job: Job? = null
    private var remainingTime: Long = _countdown.value.currentMillis

    fun startCountdown() {
        job?.cancel()
        job = viewModelScope.launch {
            createCountdown(remainingTime).collect {
                _countdown.value = it
                remainingTime = it.currentMillis
            }
        }
    }

    fun pauseCountdown() {
        job?.cancel()
        updateCountdownState(CountDownState.PAUSED)
    }

    fun resumeCountdown() {
        startCountdown()
    }

    fun cancelCountdown() {
        job?.cancel()
        updateCountdownState(CountDownState.INIT)
        remainingTime = Constants.defaultCountdownMillis
    }

    private fun updateCountdownState(newState: CountDownState) {
        viewModelScope.launch {
            if (newState == CountDownState.INIT) {
                _countdown.emit(CountdownDto(CountDownState.INIT, Constants.defaultCountdownMillis))
            }
            _countdown.emit(_countdown.value.copy(currentState = newState))
        }
    }

    private fun createCountdown(initialTime: Long) = flow {
        var remainingTime = initialTime

        while (remainingTime>0) {
            Log.d("asdf", remainingTime.toString())
            emit(CountdownDto(CountDownState.RUNNING, remainingTime))
            delay(49)
            remainingTime -= 49
        }

        val background = isAppIsInBackground(context)
        Log.d("BValue", background.toString())
        if (background) {
            notify(context, "Timer's Completed", "1 minute timer is completed.")
        }
        emit(CountdownDto(CountDownState.INIT, Constants.defaultCountdownMillis))
    }
}