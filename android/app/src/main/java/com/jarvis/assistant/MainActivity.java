package com.jarvis.assistant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "JARVIS_MainActivity";
    private WebView webView;
    
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView = findViewById(R.id.webview);
        
        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        
        // Set WebView clients
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "Page loaded successfully: " + url);
            }
            
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(TAG, "WebView error: " + error.getDescription());
            }
        });
        
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG, "Console: " + consoleMessage.message() + 
                      " -- From line " + consoleMessage.lineNumber() + 
                      " of " + consoleMessage.sourceId());
                return true;
            }
        });
        
        // Load the JARVIS interface from assets
        try {
            webView.loadUrl("file:///android_asset/www/index.html");
            Log.d(TAG, "Loading JARVIS interface...");
        } catch (Exception e) {
            Log.e(TAG, "Error loading JARVIS interface", e);
            Toast.makeText(this, "Error loading JARVIS interface", Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
