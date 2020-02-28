package com.toucanpay.utils.utils

import android.os.Build
import com.toucanpay.ToucanPayApp
import java.util.*

fun isMarshmallowOrHigher() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun dpToPx(dp: Int) = dp * ToucanPayApp.instance.resources.displayMetrics.density.toInt()