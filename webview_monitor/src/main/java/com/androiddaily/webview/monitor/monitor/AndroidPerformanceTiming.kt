package com.androiddaily.webview.monitor.monitor

import android.webkit.JavascriptInterface

abstract class AndroidPerformanceTiming {

    @JavascriptInterface
    fun sendResource(timingJsn: String) {
        handleTiming(timingJsn)
    }

    abstract fun handleTiming(timingJsn: String)
}