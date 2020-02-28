package com.toucanpay.ui.components.swaprequestsuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.toucanpay.R
import com.toucanpay.ui.base.BaseBottomSheetDialogFragment
import com.toucanpay.utils.extensions.toBigDecimalFormat
import kotlinx.android.synthetic.main.dialog_success_swap_request.*
import pl.droidsonroids.gif.GifDrawable
import java.math.BigDecimal

class SwapRequestSuccessDialog: BaseBottomSheetDialogFragment() {

    private val args by navArgs<SwapRequestSuccessDialogArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_success_swap_request, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (gifSuccess.drawable as GifDrawable).loopCount = 1

        successText.isGone = args.swapSuccess.message.isNullOrEmpty()
        tokenSwapSuccess.visibility = if (args.swapSuccess.tokenSuccess == null) View.GONE else View.VISIBLE

        successText.text = args.swapSuccess.message

        args.swapSuccess.tokenSuccess?.let {
            outcomeTokenSymbol.apply {
                text = it.outcomeTokenSymbol
                setBackgroundResource(R.drawable.bg_token_symbol_swap)
                DrawableCompat.setTint(this.background, it.outcomeColor)
            }
            outcomeAmountText.text = BigDecimal(it.outcomeAmount).toBigDecimalFormat()

            incomeTokenSymbol.apply {
                text = it.incomeTokenSymbol
                setBackgroundResource(R.drawable.bg_token_symbol_swap)
                DrawableCompat.setTint(this.background, it.incomeColor)
            }
            incomeAmountText.text = BigDecimal(it.incomeAmount).toBigDecimalFormat()
        }
    }
}