package com.toucanwalletdemo.ui.components.tradetokenssuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.BaseFragment
import com.toucanwalletdemo.utils.extensions.toBigDecimalFormat
import kotlinx.android.synthetic.main.fragment_trade_tokens_success.*
import org.koin.standalone.KoinComponent
import pl.droidsonroids.gif.GifDrawable

class TradeTokensSuccessFragment: BaseFragment(), KoinComponent {

    private val args by navArgs<TradeTokensSuccessFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trade_tokens_success, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (gifSuccess.drawable as GifDrawable).loopCount = 1

        closeButton.setOnClickListener {
            navController.navigateUp()
        }

        closeBottomButton.setOnClickListener {
            navController.navigateUp()
        }

        with(args.inputData) {
            titleText.text = if (args.isSendSuccess) getString(R.string.trade_success_body) else "$username " + getString(R.string.trade_success_request)
            tokenSymbolText.text = tokenSymbol
            tokenAmountText.text = amount.toBigDecimalFormat()
            usernameText.text = if (args.isSendSuccess) getString(R.string.trade_success_username, username) else ""
        }
    }

    override fun hasBottomBar() = false
}