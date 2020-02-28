package com.toucanpay.ui.components.authorize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.toucanpay.R
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.authorize.mvi.AuthorizeAction
import com.toucanpay.ui.components.authorize.mvi.AuthorizeResult
import com.toucanpay.utils.qrcode.QrCode
import com.toucanpay.utils.qrcode.QrCodeManager
import com.toucanpay.utils.utils.getFormattedTimerText
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_authorize.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class AuthorizeFragment: MviBaseFragment<AuthorizeAction, AuthorizeResult, AuthorizeViewState, AuthorizeViewModel>(
    AuthorizeViewModel::class.java
) {

    private val qrCodeManager: QrCodeManager by inject()
    private var timerDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_authorize, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (navController.currentDestination?.id == R.id.authorizeFragment) {
            navController.navigate(R.id.navigateToEnterPINDialog)
        }

        closeButton.setOnClickListener {
            navController.navigateUp()
        }

        qrCodeView.setOnClickListener {
            timerDisposable?.dispose()
            navController.navigate(R.id.navigateToEnterPINDialog)
        }

        mainViewModel.refreshQR.observe(this, Observer {
            if (it) {
                60L.start()
                viewModel.onAccountInfo()
            }
        })
    }

    override fun render(viewState: AuthorizeViewState) {
        with(viewState) {

            success?.consume {
                it?.let {
                    qrCodeView.setImageBitmap(qrCodeManager.generate(QrCode.SignatureQrCode(it)))
                }
            }

            error?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun Long.start() {
        timerDisposable = Observable.zip(
            Observable.range(1, toInt()),
            Observable.interval(1, TimeUnit.SECONDS),
            BiFunction { integer: Int, _: Long ->
                val l: Long = this - integer
                l
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                timerText.text = getFormattedTimerText(it)
            }.doOnComplete {
                timerText.text = getString(R.string.authorize_end_timer)
            }.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.refreshQR.postValue(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerDisposable?.dispose()
    }

    override fun hasBottomBar() = false
}