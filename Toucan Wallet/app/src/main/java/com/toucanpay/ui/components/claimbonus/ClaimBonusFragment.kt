package com.toucanpay.ui.components.claimbonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.toucanpay.R
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusAction
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusResult
import kotlinx.android.synthetic.main.fragment_claim_bonus.*

class ClaimBonusFragment: MviBaseFragment<ClaimBonusAction, ClaimBonusResult, ClaimBonusViewState, ClaimBonusViewModel>(
    ClaimBonusViewModel::class.java
) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_claim_bonus, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        claimButton.setOnClickListener {
            viewModel.onClaimBonus()
        }
    }

    override fun render(viewState: ClaimBonusViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            claimButton.isInvisible = inProgress

            error?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            claimBonusSuccess?.consume {
                it?.let {
                    navController.navigate(ClaimBonusFragmentDirections.navigateToClaimBonusSuccessFragment(it))
                }
            }
        }
    }
}