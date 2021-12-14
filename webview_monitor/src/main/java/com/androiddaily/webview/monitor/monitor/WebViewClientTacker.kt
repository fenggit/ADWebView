package com.androiddaily.webview.monitor.monitor

import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.concurrent.atomic.AtomicBoolean

const val Android_Performance_Timing = "AndroidPerformanceTiming"

open class WebViewClientTacker : WebViewClient() {
    /**
     * 判断网页加载是否完成
     */
    private val isWebLoadFinished = AtomicBoolean(false)

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        //可能会在进度<100或==100的情况下出现多次onPageFinished回调
        if (view!!.progress >= 98 && !isWebLoadFinished.get()) {
            //可能会回调多次
            isWebLoadFinished.set(true)
            val format = "javascript:%s.sendResource(JSON.stringify(window.performance.timing));"
            val injectJs = java.lang.String.format(format, Android_Performance_Timing)
            view.loadUrl(injectJs)
        }
    }
}