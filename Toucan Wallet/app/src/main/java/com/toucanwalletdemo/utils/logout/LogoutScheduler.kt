package com.toucanwalletdemo.utils.logout

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.toucanwalletdemo.data.repositories.UserRepository
import java.util.*

class LogoutScheduler(
    private val userRepository: UserRepository
): LifecycleObserver {

    private var timer: Timer? = null

    fun init(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        timer = Timer()
        timer?.schedule(LogoutTimerTask(userRepository), LOGOUT_DELAY)
    }

    companion object {
        const val LOGOUT_DELAY = 10 * 60 * 1000L
    }
}