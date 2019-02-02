package com.example.dario.pcbakovic.ui.scanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.base.BaseFragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_scanner.*
import timber.log.Timber

class ScannerFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        open_scanner_btn.setOnClickListener { checkCameraPermissionAndOpenScanner() }
    }

    private fun openScanner() {
        IntentIntegrator.forSupportFragment(this)
            .setOrientationLocked(false)
            .setCaptureActivity(CustomScannerActivity::class.java)
            .initiateScan()
    }

    private fun checkCameraPermissionAndOpenScanner() {
        if (ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            openScanner()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openScanner()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Timber.e("No extras found!")
            } else {
                Toast.makeText(activity, "Skenirani kod je ${result.contents}", Toast.LENGTH_LONG).show()
                Timber.d("Scanned code is $result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 2
        fun newInstance(): ScannerFragment {
            return ScannerFragment()
        }
    }
}