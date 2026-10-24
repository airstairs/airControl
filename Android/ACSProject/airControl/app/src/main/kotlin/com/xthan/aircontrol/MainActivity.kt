package com.xthan.aircontrol

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var webV: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Fixed from init to onCreate
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webV = findViewById(R.id.myWebView)
        
        // Configure WebView settings for local apps & storage
        val webSettings: WebSettings = webV.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true // Required for localStorage to persist tasks!
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true

        // Allows JS console logs to appear in your Android Studio Logcat
        webV.webChromeClient = object : WebChromeClient() {}

        if (savedInstanceState != null) {
            webV.restoreState(savedInstanceState)
        } else {
            webV.loadUrl("file:///android_asset/index.html")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webV.saveState(outState)
    }
}
