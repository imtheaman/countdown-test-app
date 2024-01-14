package com.example.timer.data.dto

data class CountdownDto(
    var currentState: CountDownState,
    var currentMillis: Long
)

enum class CountDownState(s: String) {
    PAUSED("paused"),
    INIT("init"),
    RUNNING("running")
}
