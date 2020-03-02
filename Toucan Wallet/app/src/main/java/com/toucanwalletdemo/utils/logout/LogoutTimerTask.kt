package com.toucanwalletdemo.utils.logout

import com.toucanwalletdemo.data.repositories.UserRepository
import java.util.*

class LogoutTimerTask(
    private val userRepository: UserRepository
): TimerTask() {

    override fun run() {
        userRepository.logoutUser()
    }
}