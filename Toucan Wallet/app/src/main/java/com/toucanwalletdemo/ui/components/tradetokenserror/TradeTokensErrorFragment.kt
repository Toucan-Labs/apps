package com.toucanwalletdemo.ui.components.tradetokenserror

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_trade_tokens_error.*
import org.koin.standalone.KoinComponent
import pl.droidsonroids.gif.GifDrawable

class TradeTokensErrorFragment: BaseFragment(), KoinComponent {

    private val args by navArgs<TradeTokensErrorFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trade_tokens_error, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorText.text = args.errorMessage

        (gifError.drawable as GifDrawable).loopCount = 1

        closeButton.setOnClickListener {
            navController.navigateUp()
        }

        closeBottomButton.setOnClickListener {
            navController.navigateUp()
        }
    }

    override fun hasBottomBar() = false
}