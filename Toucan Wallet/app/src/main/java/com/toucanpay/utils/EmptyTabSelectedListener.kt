package com.toucanpay.utils

import com.google.android.material.tabs.TabLayout

abstract class SimpleOnTabSelectedListener: TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        //do nothing
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        //do nothing
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        //do nothing
    }
}