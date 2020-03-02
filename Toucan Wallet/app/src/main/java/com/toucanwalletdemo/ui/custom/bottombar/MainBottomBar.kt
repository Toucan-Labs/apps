package com.toucanwalletdemo.ui.custom.bottombar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.toucanwalletdemo.R
import kotlinx.android.synthetic.main.view_bottom_bar_form.view.*
import kotlinx.android.synthetic.main.view_main_bottom_bar.view.*

class MainBottomBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var onItemSelectedListener: ((item: BottomBarItem) -> Unit)? = null

    private var prevSelectedItem: View? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_main_bottom_bar, this, true)

        initIconsTint()
        initClickListeners()
        initInsetsListener()
        initIconsAndTexts()
    }

    fun selectItem(item: BottomBarItem) {
        prevSelectedItem?.isSelected = false
        val newSelectedItem = when (item) {
            BottomBarItem.WALLETS -> walletsButton
            BottomBarItem.SWAPS -> swapsButton
            BottomBarItem.SCANNER -> scannerButton
            BottomBarItem.MESSAGES -> messagesButton
            BottomBarItem.ACCOUNT -> accountButton
        }
        newSelectedItem.isSelected = true
        prevSelectedItem = newSelectedItem
        onItemSelectedListener?.invoke(item)
    }

    private fun initIconsTint() =
        ContextCompat.getColorStateList(context, R.color.bg_bottom_bar_button).let {
            walletsButton.imageView.imageTintList = it
            walletsButton.bottomText.setTextColor(it)
            swapsButton.imageView.imageTintList = it
            swapsButton.bottomText.setTextColor(it)
            scannerButton.imageView.imageTintList = it
            scannerButton.bottomText.setTextColor(it)
            messagesButton.imageView.imageTintList = it
            messagesButton.bottomText.setTextColor(it)
            accountButton.imageView.imageTintList = it
            accountButton.bottomText.setTextColor(it)
        }

    private fun initIconsAndTexts() = with(context) {
        walletsButton.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_balance_wallet_black_24dp))
        walletsButton.bottomText.text = getString(R.string.wallets_title)
        swapsButton.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_swap_vertical_circle_black_24dp))
        swapsButton.bottomText.text = getString(R.string.swaps_title)
        scannerButton.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_filter_center_focus_black_24dp))
        scannerButton.bottomText.text = getString(R.string.scanner_title)
        messagesButton.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_message_black_24dp))
        messagesButton.bottomText.text = getString(R.string.messages_title)
        accountButton.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_security_black_24dp))
        accountButton.bottomText.text = getString(R.string.account_title)
    }

    private fun initClickListeners() {
        walletsButton.setOnClickListener {
            selectItem(BottomBarItem.WALLETS)
        }
        swapsButton.setOnClickListener {
            selectItem(BottomBarItem.SWAPS)
        }
        scannerButton.setOnClickListener {
            selectItem(BottomBarItem.SCANNER)
        }
        messagesButton.setOnClickListener {
            selectItem(BottomBarItem.MESSAGES)
        }
        accountButton.setOnClickListener {
            selectItem(BottomBarItem.ACCOUNT)
        }
    }

    private fun initInsetsListener() {
        setOnApplyWindowInsetsListener { _, insets ->
            if (!insets.isConsumed) {
                mainView.setPadding(paddingLeft, paddingTop, paddingRight, insets.systemWindowInsetBottom)
            }
            insets
        }
    }
}

enum class BottomBarItem {
    WALLETS,
    SWAPS,
    SCANNER,
    MESSAGES,
    ACCOUNT
}