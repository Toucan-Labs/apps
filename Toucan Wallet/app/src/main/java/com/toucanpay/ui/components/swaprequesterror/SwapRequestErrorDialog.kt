package com.toucanpay.ui.components.swaprequesterror

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.toucanpay.R
import com.toucanpay.ui.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_error_swap_request.*

class SwapRequestErrorDialog: BaseBottomSheetDialogFragment() {

    private val args by navArgs<SwapRequestErrorDialogArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_error_swap_request, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorText.isGone = args.swapError.message.isNullOrEmpty()
        insufficientBalanceText.visibility = if (args.swapError.tokenError == null) View.GONE else View.VISIBLE

        errorText.text = args.swapError.message

        args.swapError.tokenError?.let {
            tokenText.apply {
                text = it.tokenSymbol
                setBackgroundResource(R.drawable.bg_token_symbol_swap)
                DrawableCompat.setTint(this.background, it.tokenColor)
            }
        }
    }
}