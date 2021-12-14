package com.androiddaily.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.daily.GridInfo
import com.android.daily.GridRecyclerViewAdapter
import com.androiddaily.webview.databinding.ActivityMainBinding
import com.androiddaily.webview.monitor.WebViewActivity


class MainActivity : AppCompatActivity() {
    var list: ArrayList<GridInfo> = arrayListOf(
        GridInfo("WebView", WebViewActivity::class.java),
        GridInfo("Handler", WebViewActivity::class.java),
        GridInfo("View", WebViewActivity::class.java),
        GridInfo("View", WebViewActivity::class.java),
        GridInfo("View", WebViewActivity::class.java),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3)
            recyclerView.adapter = GridRecyclerViewAdapter(this@MainActivity, list)
            recyclerView.addItemDecoration(CommonDecoration(this@MainActivity))
        }
    }
}