package com.toucanwalletdemo.utils.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart

fun getSwapCardWidth(view: View, context: Context): Int =
    DisplayMetrics().apply {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .also { it.defaultDisplay.getMetrics(this) }
    }.let {
        ((it.widthPixels - view.marginStart - view.marginEnd) * 0.85).toInt()
    }

fun getFormattedTimerText(seconds: Long) =
    String.format("%02d:%02d", seconds / 60, seconds % 60)

fun cycleTextViewExpansion(tv: TextView): ObjectAnimator =
    ObjectAnimator.ofInt(tv, "maxLines", if (tv.maxLines == 3) tv.lineCount else 3).apply {
        setDuration(200).start()
    }