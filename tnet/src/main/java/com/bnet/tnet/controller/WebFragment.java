package com.bnet.tnet.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bnet.tnet.R;

/**
 * Created by שי on 04/02/2017.
 */

public class WebFragment extends Fragment {

    private ProgressBar webViewProgressBar;
    private WebView agencyWebView;
    public WebFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String link = bundle.getString("LINK");
        findViews();
        agencyWebView.getSettings().setJavaScriptEnabled(true);
        agencyWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webViewProgressBar.setProgress(newProgress);
            }
        });
        agencyWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        openLinkInWebView(link);
    }

    private void findViews()
    {
        webViewProgressBar=(ProgressBar)getActivity().findViewById(R.id.webViewProgressBar);
        agencyWebView=(WebView) getActivity().findViewById(R.id.agencyWebView);

    }
    private void openLinkInWebView(String link) {
        agencyWebView.loadUrl(addHttpIfNeeded(link));
    }
    private String addHttpIfNeeded(String url) {
        if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
            url = "www."+url;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://"+url;
        }
        return url;
    }
    public boolean goBack()
    {
        if(agencyWebView.canGoBack()) {
            agencyWebView.goBack();
            return true;
        }
        return false;
    }

    public void goHome() {
        agencyWebView.loadUrl(getArguments().getString("LINK"));
    }
}
