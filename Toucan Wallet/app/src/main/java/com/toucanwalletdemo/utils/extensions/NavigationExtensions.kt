package com.toucanwalletdemo.utils.extensions

import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.navigateIfNotIn(@IdRes id: Int) {
    if (currentDestination?.id != id) {
        navigate(id)
    }
}