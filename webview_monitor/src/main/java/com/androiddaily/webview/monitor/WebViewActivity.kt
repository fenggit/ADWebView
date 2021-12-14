package com.androiddaily.webview.monitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddaily.webview.monitor.blank.WebViewBlankActivity
import com.androiddaily.webview.monitor.databinding.ActivityWebViewHomeBinding
import com.androiddaily.webview.monitor.monitor.WebViewWebMonitorActivity

/**
 * 1.WebView 页面监控
 * 2.WebView 白屏检测
 */
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebViewHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
//            toolbar.title = "WebView 专题"
//            toolbar.setTitleTextColor(Color.WHITE)
//            setSupportActionBar(toolbar)

            btnBlank.setOnClickListener {
                startActivity(Intent(this@WebViewActivity, WebViewBlankActivity::class.java))

            }

            btnMonitor.setOnClickListener {
                startActivity(Intent(this@WebViewActivity, WebViewWebMonitorActivity::class.java))
            }
        }
    }
}