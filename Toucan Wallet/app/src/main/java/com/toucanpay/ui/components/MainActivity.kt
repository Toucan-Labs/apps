package com.toucanpay.ui.components

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.toucanpay.R
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.BaseActivity
import com.toucanpay.ui.custom.bottombar.BottomBarItem
import com.toucanpay.utils.extensions.navigateIfNotIn
import com.toucanpay.utils.logout.LogoutScheduler
import com.toucanpay.utils.logout.OnLogoutUserService
import com.toucanpay.utils.utils.alwaysHiddenSoftInput
import com.toucanpay.utils.utils.dpToPx
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MainActivity: BaseActivity(), KoinComponent {

    private val userRepository: UserRepository by inject()
    private val logoutScheduler: LogoutScheduler by inject()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, OnLogoutUserService::class.java))
        logoutScheduler.init(this.lifecycle)
        navController = NavHostFragment.findNavController(mainNavHostFragment)
        initBottomBar()
    }

    override fun onStart() {
        super.onStart()
        when {
            !userRepository.isUserRegistered() -> navController.navigateIfNotIn(R.id.showSignUpFragment)
            !userRepository.isUserVerified() -> navController.navigateIfNotIn(R.id.showVerifyFragment)
            !userRepository.isUserLoggedIn() -> navController.navigateIfNotIn(R.id.showSignInFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        alwaysHiddenSoftInput(this)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    fun refreshBottomBar(isVisible: Boolean, drawBelowBottomBar: Boolean) {
        mainBottomBar.visibility = if (isVisible) View.VISIBLE else View.GONE
        fragmentBorder.apply {
            layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
                val marginTop = dpToPx(if (drawBelowBottomBar) 16 else 0)
                setMargins(marginLeft, marginTop, marginRight, marginBottom)
            }
        }
    }

    fun selectBottomBarItem(item: BottomBarItem) {
        mainBottomBar.selectItem(item)
    }

    private fun initBottomBar() {
        mainBottomBar.onItemSelectedListener = ::onItemSelected
        selectBottomBarItem(BottomBarItem.WALLETS)
    }

    private fun onItemSelected(item: BottomBarItem) {
        val actionId = when (item) {
            BottomBarItem.WALLETS -> R.id.showWalletsFragment
            BottomBarItem.SWAPS -> R.id.showSwapsFragment
            BottomBarItem.SCANNER -> R.id.showScannerFragment
            BottomBarItem.MESSAGES -> R.id.showMessagesFragment
            BottomBarItem.ACCOUNT -> R.id.showAccountFragment
        }
        navController.navigate(actionId)
    }
}