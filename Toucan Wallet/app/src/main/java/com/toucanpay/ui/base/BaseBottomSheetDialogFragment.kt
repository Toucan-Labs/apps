package com.toucanpay.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {

    protected lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = NavHostFragment.findNavController(this)
        setOnShowListener()
    }

    private fun setOnShowListener() = dialog?.setOnShowListener {
        (it as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet).apply {
            BottomSheetBehavior.from(this).apply {
                peekHeight = 0
                state = BottomSheetBehavior.STATE_EXPANDED
                setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            this@BaseBottomSheetDialogFragment.dismiss()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                })
            }
        }
    }
}