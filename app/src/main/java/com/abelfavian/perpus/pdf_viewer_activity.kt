package com.abelfavian.perpus

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pdf_viewer_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pdf_viewer)
        val webView: WebView = findViewById(R.id.webView)
        val pdfUrl = intent.getStringExtra("PDF_URL") ?: ""

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.pluginState = WebSettings.PluginState.ON

        val googleViewerUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url="
        webView.loadUrl(googleViewerUrl + pdfUrl)
    }
        }

