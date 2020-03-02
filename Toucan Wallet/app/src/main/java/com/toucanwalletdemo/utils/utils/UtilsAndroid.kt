package com.toucanwalletdemo.utils.utils

import android.os.Build
import com.toucanwalletdemo.ToucanPayApp

fun isMarshmallowOrHigher() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun dpToPx(dp: Int) = dp * ToucanPayApp.instance.resources.displayMetrics.density.toInt()