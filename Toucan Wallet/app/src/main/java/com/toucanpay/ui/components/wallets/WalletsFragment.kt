package com.toucanpay.ui.components.wallets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.toucanpay.R
import com.toucanpay.data.models.Balance
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.wallets.balances.BalancesAdapter
import com.toucanpay.ui.components.wallets.mvi.WalletsAction
import com.toucanpay.ui.components.wallets.mvi.WalletsResult
import com.toucanpay.utils.SimpleOnTabSelectedListener
import kotlinx.android.synthetic.main.fragment_wallets.*

class WalletsFragment: MviBaseFragment<WalletsAction, WalletsResult, WalletsViewState, WalletsViewModel>(
    WalletsViewModel::class.java
) {

    private val balancesAdapter = BalancesAdapter()
    private var position: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_wallets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradeButton.setOnClickListener {
            navController.navigate(R.id.navigateToTradeFragment)
        }

        initTabLayout()
        initBalancesRecyclerView()
    }

    private fun initTabLayout() = walletsTab.apply {
        addTab(newTab().setText(getString(R.string.wallets_tab_1)))
        addTab(newTab().setText(getString(R.string.wallets_tab_2)))
        addTab(newTab().setText(getString(R.string.wallets_tab_3)))
    }

    private fun initBalancesRecyclerView() {
        balancesAdapter.onItemClickListener = {
            position = walletsTab.selectedTabPosition
            navController.navigate(WalletsFragmentDirections.navigateToWalletDetailsFragment(it))
        }
        balancesRecyclerView.adapter = balancesAdapter
    }

    override fun onResume() {
        super.onResume()
        position?.let {
            walletsTab.getTabAt(it)?.select()
            position = null
        }
    }

    override fun render(viewState: WalletsViewState) = with(viewState) {
        progressBar.isVisible = inProgress
        balancesRecyclerView.isInvisible = balances.isNullOrEmpty()

        if (!balances.isNullOrEmpty()) {
            if (walletsTab.selectedTabPosition == 0) {
                balancesAdapter.setBalance(balances.filter { it.balance != "0" })
            }
            setBalancesWatcher(balances)
        }
    }

    private fun setBalancesWatcher(list: List<Balance>) {
        walletsTab.addOnTabSelectedListener(object: SimpleOnTabSelectedListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> balancesAdapter.setBalance(list.filter { it.balance != "0" })
                    1 -> balancesAdapter.setBalance(list.filter { it.balance == "0" })
                    2 -> balancesAdapter.setBalance(list.filter { it.balance == "0.001" })
                }
            }
        })
    }
}