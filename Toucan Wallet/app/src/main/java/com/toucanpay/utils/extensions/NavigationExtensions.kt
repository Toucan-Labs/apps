package com.toucanpay.utils.extensions

import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.navigateIfNotIn(@IdRes id: Int) {
    if (currentDestination?.id != id) {
        navigate(id)
    }
}