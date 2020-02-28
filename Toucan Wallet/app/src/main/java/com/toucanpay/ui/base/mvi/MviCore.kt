package com.toucanpay.ui.base.mvi

import android.os.Parcelable

interface MviAction

interface MviResult

interface MviViewState<R: MviResult>: Parcelable {

    fun isSavable(): Boolean

    fun reduce(result: R): MviViewState<R>
}