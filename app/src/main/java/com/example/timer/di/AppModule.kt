package com.example.timer.di

import com.example.timer.presentation.countdown.CountdownViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidContext

val AppModule = module {
    viewModel { CountdownViewModel(androidContext()) }
}