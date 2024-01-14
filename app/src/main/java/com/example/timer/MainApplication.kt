package com.example.timer

import android.app.Application
import com.example.timer.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(AppModule)
        }
    }
}