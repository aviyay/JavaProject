package com.bnet.tnet.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bnet.tnet.R;

public class WebActivity extends AppCompatActivity {

    private ProgressBar webViewProgressBar;
    private WebView agencyWebView;
    private String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        link=getIntent().getStringExtra("LINK");
        findViews();

        agencyWebView.getSettings().setJavaScriptEnabled(true);
        agencyWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webViewProgressBar.setProgress(newProgress);
            }
        });
        agencyWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url) {
                view.loadUrl(url);
                return false;
            }
        });
        openLinkInWebView(link.toString());
        Toast.makeText(this,"Press Double BACK to exit",Toast.LENGTH_LONG).show();
    }
    private void findViews()
    {
        webViewProgressBar=(ProgressBar)findViewById(R.id.webViewProgressBar);
        agencyWebView=(WebView) findViewById(R.id.agencyWebView);
    }
    private void openLinkInWebView(String link) {
        agencyWebView.loadUrl(addHttpIfNeeded(link));
    }
    private String addHttpIfNeeded(String url)
    {
        if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
            url = "www."+url;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://"+url;
        }
        return url;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        if(agencyWebView.canGoBack())
            agencyWebView.goBack();
        else {
            Toast.makeText(this,"Press Double BACK to exit",Toast.LENGTH_LONG).show();
        }
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 500);
    }
}
