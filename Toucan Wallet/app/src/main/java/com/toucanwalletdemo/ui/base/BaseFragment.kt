package com.toucanwalletdemo.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toucanwalletdemo.ui.components.MainActivity
import com.toucanwalletdemo.ui.components.MainViewModel
import com.toucanwalletdemo.ui.custom.bottombar.BottomBarItem

abstract class BaseFragment: Fragment() {

    protected lateinit var navController: NavController

    protected val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initInsetsListener(view)

        /**
         * Each new fragment view need to request insets in order to add proper status bar padding.
         * It is not done automatically when doing fragment balances using navigationController
         */
        view.requestApplyInsets()
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as? MainActivity?)?.refreshBottomBar(hasBottomBar(), drawBelowBottomBar())
    }

    private fun initInsetsListener(view: View) = with(view) {
        setOnApplyWindowInsetsListener { _, insets ->
            if (!insets.isConsumed) {
                val bottomPadding = if (hasBottomBar()) paddingBottom else insets.systemWindowInsetBottom
                setPadding(paddingLeft, paddingTop, paddingRight, bottomPadding)
            }
            insets
        }
    }

    protected fun selectInitialTab() {
        (requireActivity() as? MainActivity?)?.selectBottomBarItem(BottomBarItem.WALLETS)
    }

    protected open fun hasBottomBar() = true

    protected open fun drawBelowBottomBar() = false
}