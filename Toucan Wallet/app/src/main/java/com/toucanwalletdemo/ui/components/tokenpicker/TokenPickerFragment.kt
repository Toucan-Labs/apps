package com.toucanwalletdemo.ui.components.tokenpicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.tokenpicker.mvi.TokenPickerAction
import com.toucanwalletdemo.ui.components.tokenpicker.mvi.TokenPickerResult
import com.toucanwalletdemo.ui.components.tokenpicker.tokens.TokenAdapter
import com.toucanwalletdemo.utils.SimpleOnQueryTextListener
import com.toucanwalletdemo.utils.utils.hideSoftInput
import kotlinx.android.synthetic.main.fragment_token_picker.*
import java.util.*

class TokenPickerFragment: MviBaseFragment<TokenPickerAction, TokenPickerResult, TokenPickerViewState, TokenPickerViewModel>(
    TokenPickerViewModel::class.java
) {

    private val tokensAdapter = TokenAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_token_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            hideSoftInput(requireContext(), view)
            navController.navigateUp()
        }

        initTokensRecyclerView(view)
    }

    private fun initTokensRecyclerView(view: View) {
        tokensRecyclerView.adapter = tokensAdapter

        tokensAdapter.onItemClickListener = {
            hideSoftInput(requireContext(), view)
            mainViewModel.token = it
            navController.navigateUp()
        }
    }

    override fun render(viewState: TokenPickerViewState) {
        with(viewState) {
            tokensRecyclerView.isInvisible = tokens.isNullOrEmpty()

            if (!tokens.isNullOrEmpty()) {
                progressBar.isGone = true
                tokensAdapter.setTokens(tokens)
                setTextWatcher(tokens)
            }
        }
    }

    private fun setTextWatcher(tokens: List<Token>) {
        searchToken.setOnQueryTextListener(object: SimpleOnQueryTextListener() {
            override fun onQueryTextChange(newText: String): Boolean {
                val list = tokens.filter {
                    it.tokenName.toLowerCase(Locale.UK).contains(newText.toLowerCase(Locale.UK))
                            || it.tokenSymbol.toLowerCase(Locale.UK).contains(newText.toLowerCase(Locale.UK))
                }
                tokensAdapter.filteredList(list)
                return true
            }
        })
    }

    override fun hasBottomBar() = false
}