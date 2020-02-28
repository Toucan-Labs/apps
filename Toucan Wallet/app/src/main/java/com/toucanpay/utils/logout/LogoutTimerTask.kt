package com.toucanpay.utils.logout

import com.toucanpay.data.repositories.UserRepository
import java.util.*

class LogoutTimerTask(
    private val userRepository: UserRepository
): TimerTask() {

    override fun run() {
        userRepository.logoutUser()
    }
}