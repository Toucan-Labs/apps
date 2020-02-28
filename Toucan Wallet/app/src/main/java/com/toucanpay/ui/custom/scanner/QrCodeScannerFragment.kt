package com.toucanpay.ui.custom.scanner

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.toucanpay.R
import kotlinx.android.synthetic.main.fragment_qr_code_scanner.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class QrCodeScannerFragment: Fragment() {

    var qrCodeListener: ((String) -> Unit)? = null
    var closeButtonListener: (() -> Unit)? = null

    private val cameraPermissionGranted = MutableLiveData<Boolean>()
    private var qrView: QRCodeReaderView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_qr_code_scanner, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraPermissionGranted.observe(this, Observer {
            missingPermissionText.visibility = if (it) View.GONE else View.VISIBLE
        })

        if (closeButtonListener != null) {
            closeButton.isVisible = true
            closeButton.setOnClickListener { closeButtonListener?.invoke() }
        }
    }

    override fun onStart() {
        super.onStart()
        initCameraWithPermissionCheck()
    }

    override fun onStop() {
        super.onStop()
        qrView?.stopCamera()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun initCamera() {
        cameraPermissionGranted.postValue(true)

        QRCodeReaderView(context).apply {
            setQRDecodingEnabled(true)
            setOnQRCodeReadListener { text, _ ->
                qrCodeListener?.invoke(text)
            }

            qrViewContainer.addView(this)
            startCamera()
        }
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraPermissionDenied() {
        cameraPermissionGranted.postValue(false)
    }
}