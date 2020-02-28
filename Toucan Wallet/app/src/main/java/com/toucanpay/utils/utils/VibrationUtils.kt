package com.toucanpay.utils.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

@Suppress("DEPRECATION")
fun vibrate(activity: Activity) =
    (activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            this.vibrate(500)
        }
    }