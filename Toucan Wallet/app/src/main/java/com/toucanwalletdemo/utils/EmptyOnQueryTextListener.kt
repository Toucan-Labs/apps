package com.toucanwalletdemo.utils

import androidx.appcompat.widget.SearchView

abstract class SimpleOnQueryTextListener: SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }
}