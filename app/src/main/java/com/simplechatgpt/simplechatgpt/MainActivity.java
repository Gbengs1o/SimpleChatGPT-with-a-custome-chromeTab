package com.simplechatgpt.simplechatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button openChatButton;
    private WebView webView;


    private static Context mContext;

    // url for loading in custom chrome tab
    String url = "https://chat.openai.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openChatButton = findViewById(R.id.openChatButton);

        webView = findViewById(R.id.web);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Handle navigation within the WebView
        webView.setWebViewClient(new WebViewClient());

        // Display progress using a ProgressBar
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        // Load a URL in the WebView
        webView.loadUrl("https://9jatechsibs.com/simplechatgptadpage-2/");

        openChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // initializing object for custom chrome tabs.
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setShowTitle(false);
                customIntent.addDefaultShareMenuItem();

                customIntent.setInstantAppsEnabled(false);
                customIntent .enableUrlBarHiding();



                // below line is setting toolbar color
                // for our custom chrome tab.
                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.purple_200));



                // we are calling below method after
                // setting our toolbar color.
                openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(url));

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }



    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // Disable the "Open in Chrome" feature
            customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + activity.getPackageName()));

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }





}
