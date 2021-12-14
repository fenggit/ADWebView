package com.androiddaily.webview.monitor.blank

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.androiddaily.webview.monitor.databinding.ActivityWebViewBinding

/**
 *  https://juejin.cn/post/6972901939197509669
 *
 *  WebView白屏检测与处理:
 *
 *  1.onPageFinished，你可以看下原网页里面，是不是有 iFrame.我以前遇到过，嵌了N个iframe就onPageFinished N次
 *  2.可以加个防抖的逻辑，1-2秒内只做一次检测
 *  3.应该是x5 SDK有bug，导致onPageFinished 进度100 时会回调两次，这里做个缓存
 */
class WebViewBlankActivity : AppCompatActivity() {
    //应该是x5 SDK有bug，导致onPageFinished 进度100 时会回调两次，这里做个缓存
    var completedPageCache = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 5.0以上进行了优化先渲染一部分，滚动再渲染导致
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }

        val binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                //会出现多次进入onPageFinished的情况，最后一次进度为100
                if (completedPageCache.contains(url)) return;

                //进度为100
                if (binding.webview.progress > 99) {
                    completedPageCache.add(url!!)
                    //开始截图
                    //new WebWhiteChecker(WebActivity.this, webView, url).startCheck();
                }


                super.onPageFinished(view, url)

            }
        }
    }
}