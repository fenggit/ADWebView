package com.androiddaily.webview.monitor.monitor

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.webkit.WebView
import kotlin.concurrent.thread

class WebWhiteChecker(var activity: Activity, var webView: WebView?) {
    fun startCheck() {
        webView?.postDelayed({
            try {
                //Activity不处于被销毁的状态
                if (!activity.isDestroyed && !activity.isFinishing) {
                    webView?.let {
                        //这里取一半大小，不然可能OOM
                        // val bitmap = Bitmap.createBitmap(it.width / 2, it.height / 2, Bitmap.Config.ARGB_8888)
                        val bitmap = snapshotByWebView(it, Int.MAX_VALUE, Bitmap.Config.RGB_565, 0f)
                        //这里必须设置0.5f, 跟上方bitmap的缩放比例一致，不然无法截图
//                        it.snapshotVisible(bitmap, false, false, false, false, 0.5f, 0.5f) {
//                            //开始检测
//                            checkOnSubThread(bitmap)
//                        }
                    }
                }
            } catch (e: Exception) {
                //L.e(e)
            }
        }, 1000)
    }

    fun snapshotByWebView(
        webView: WebView,
        maxHeight: Int,
        config: Bitmap.Config,
        scale: Float
    ): Bitmap? {
        try {
            var newScale: Float = scale
            if (newScale <= 0) {
                // 该方法已抛弃, 可通过 setWebViewClient
                // onScaleChanged(WebView view, float oldScale, float newScale)
                // 存储并传入 newScale
                newScale = webView.scale
            }
            val width = webView.width
            var height = (webView.contentHeight * newScale + 0.5).toInt()
            // 重新设置高度
            height = Math.min(height, maxHeight)
            // 创建位图
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.TRANSPARENT)
            webView.draw(canvas)
            return bitmap
        } catch (e: java.lang.Exception) {
            //LogPrintUtils.eTag(CapturePictureUtils.TAG, e, "snapshotByWebView SDK_INT >= 21(5.0)")
        }
        return null
    }

    //白点计数
    private var whitePixelCount = 0

    private fun checkOnSubThread(bitmap: Bitmap) {
        //异步线程执行
        thread {
            val width = bitmap.width
            val height = bitmap.height

            for (x in 0 until width) {
                for (y in 0 until height) {
                    if (bitmap.getPixel(x, y) == -1) {//表示是白色
                        whitePixelCount++
                    }
                }
            }

            if (whitePixelCount > 0) {
                val rate = whitePixelCount * 100f / width / height
                //这里可以对比设定的上限，然后做处理
            }
            bitmap.recycle()
        }
    }


}