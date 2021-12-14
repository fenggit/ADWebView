package com.androiddaily.webview.monitor.monitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import com.androiddaily.webview.monitor.databinding.ActivityWebViewMonitorBinding
import com.google.gson.Gson

class WebViewWebMonitorActivity : AppCompatActivity() {
    var buffer = StringBuffer()
    var createWebView = 0L
    lateinit var binding: ActivityWebViewMonitorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createWebView = System.currentTimeMillis()

        binding = ActivityWebViewMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            buffer.append(">>> https://cn.bing.com <<<")
            buffer.append("\n")
            textviewMonitor.text = buffer.toString()

            webview.settings.javaScriptEnabled = true
            webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE

            webview.webViewClient = WebViewClientTacker()
            webview.addJavascriptInterface(object : AndroidPerformanceTiming() {
                override fun handleTiming(timingJsn: String) {
                    Log.e("hefeng", "timingJsn:$timingJsn")

                    val timing = Gson().fromJson(timingJsn, PerformanceTiming::class.java)
                    runOnUiThread {
                        timing?.apply {
                            //buffer.append(timingJsn)
                            buffer.append("HTML加载完成时间:" + (responseEnd - fetchStart))
                            buffer.append("\n")
                            buffer.append("HTML解析完成时间:" + (domInteractive - fetchStart))
                            buffer.append("\n")
                            buffer.append("DNS查询:" + (domainLookupEnd - domainLookupStart))
                            buffer.append("\n")
                            buffer.append("TCP连接:" + (connectEnd - connectEnd))
                            buffer.append("\n")
                            buffer.append("HTTP请求:" + (responseEnd - responseStart))
                            buffer.append("\n")
                            buffer.append("DOM解析:" + (domComplete - domInteractive))
                            buffer.append("\n")
                            buffer.append("白屏时间：" + (responseStart - navigationStart))
                            buffer.append("\n")
                            buffer.append("WebView创建：" + (navigationStart - createWebView))
                            buffer.append("\n")
                            //buffer.append("HTTP 头部大小：" + (transferSize - encodedBodySize))
                            buffer.append("\n")
                        }
                        textviewMonitor.text = buffer.toString()
                    }
                }
            }, Android_Performance_Timing)

            webview.loadUrl("https://cn.bing.com/")
        }
    }

    override fun onDestroy() {
        binding.webview.clearCache(true)
        binding.webview.clearHistory()
        binding.webview.destroy()
        super.onDestroy()
    }
}