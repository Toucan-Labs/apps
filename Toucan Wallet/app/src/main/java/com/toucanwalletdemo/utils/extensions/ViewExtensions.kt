package com.toucanwalletdemo.utils.extensions

import android.graphics.Outline
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.LayoutRes

fun ViewGroup.inflateItem(@LayoutRes layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)

fun ImageView.setImageViewBackground(
    color: Int,
    curve: Float
) {

    setColorFilter(color, PorterDuff.Mode.SCREEN)

    outlineProvider = object: ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) =
            outline.setRoundRect(0, 0, view.width, view.height, curve)
    }
    clipToOutline = true
}