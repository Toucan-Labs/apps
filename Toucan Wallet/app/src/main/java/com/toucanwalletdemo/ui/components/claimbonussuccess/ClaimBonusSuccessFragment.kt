package com.toucanwalletdemo.ui.components.claimbonussuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.repositories.RegisterRewardRepository
import com.toucanwalletdemo.ui.base.BaseFragment
import com.toucanwalletdemo.utils.extensions.toBigDecimalFormat
import kotlinx.android.synthetic.main.fragment_claim_bonus_success.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import pl.droidsonroids.gif.GifDrawable
import java.math.BigDecimal

class ClaimBonusSuccessFragment: BaseFragment(), KoinComponent {

    private val args by navArgs<ClaimBonusSuccessFragmentArgs>()
    private val rewardRepository: RegisterRewardRepository by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_claim_bonus_success, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (gifSuccess.drawable as GifDrawable).loopCount = 1

        closeButton.setOnClickListener {
            exitScreen()
        }

        checkWalletButton.setOnClickListener {
            exitScreen()
        }

        tokenSymbolText.text = args.reward.tokenSymbol
        tokenAmountText.text = BigDecimal(args.reward.amount).toBigDecimalFormat()
    }

    private fun exitScreen() {
        selectInitialTab()
        rewardRepository.deleteReward()
    }

    override fun hasBottomBar() = false
}