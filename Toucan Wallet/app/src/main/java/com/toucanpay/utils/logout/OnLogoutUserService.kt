package com.toucanpay.utils.logout

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.toucanpay.data.repositories.UserRepository
import org.koin.android.ext.android.inject

class OnLogoutUserService: Service() {

    private val userRepository: UserRepository by inject()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        userRepository.logoutUser()
        stopSelf()
    }
}