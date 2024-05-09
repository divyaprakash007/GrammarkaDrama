package com.grammarkadrama.activity

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.grammarkadrama.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webView: WebView = findViewById(R.id.webView)

        // Retrieve the intent and check which button was clicked
        val intent = intent
        val buttonClicked = intent.getStringExtra("buttonClicked")

        // Load local HTML file based on which button was clicked
        if (buttonClicked == "aboutUsTV") {
            webView.loadUrl("file:///android_asset/about_us.html")
        } else if (buttonClicked == "policyTV") {
            webView.loadUrl("file:///android_asset/privacy_policy.html")
        }
    }
}
