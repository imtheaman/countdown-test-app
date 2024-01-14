package com.example.timer.common

import android.app.ActivityManager
import android.content.Context

fun isAppIsInBackground(context : Context) : Boolean{
    var isInBackground = true
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess.equals(context.packageName)) {
                        isInBackground = false
                    }
                }
            }
        }
    return isInBackground
}