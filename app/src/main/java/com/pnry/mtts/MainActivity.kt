// Code are generated with the help of ChatGPT etc...


package com.pnry.mtts

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
// import android.content.Context



class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        // This is useful in immersive mode, however may cause the keyboard covering form
        // WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.isVerticalScrollBarEnabled = true
        webView.isHorizontalScrollBarEnabled = true
        // Enable cache
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.settings.domStorageEnabled = true

        // Set WebViewClient to override shouldOverrideUrlLoading method
//        webView.webViewClient = object : WebViewClient() {
//            @Deprecated("Deprecated in Java", ReplaceWith("false"))
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                return false
//            }
//        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // Handle the URL loading as desired
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.loadUrl("https://dpc-mmstts.hf.space")
        // Set up onBackPressedCallback to handle the back button press in WebView
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Make the app full screen and hide the status bar and navigation bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // val controller = WindowCompat.getInsetsController(window, window.decorView)
            val controller = WindowInsetsControllerCompat(window, window.decorView)

            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    )
        }
    }

}
