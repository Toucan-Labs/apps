package com.toucanwalletdemo.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.toucanwalletdemo.ui.components.MainActivity
import com.toucanwalletdemo.ui.components.MainViewModel

abstract class BaseDialogFragment: DialogFragment() {

    protected lateinit var navController: NavController

    val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        navController = NavHostFragment.findNavController(this)
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as? MainActivity?)?.refreshBottomBar(hasBottomBar(), drawBelowBottomBar())
    }

    fun hasBottomBar() = false

    private fun drawBelowBottomBar() = false
}