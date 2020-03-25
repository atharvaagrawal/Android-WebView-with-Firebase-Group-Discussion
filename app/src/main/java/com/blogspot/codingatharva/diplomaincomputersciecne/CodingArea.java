package com.blogspot.codingatharva.diplomaincomputersciecne;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CodingArea extends AppCompatActivity {
    WebView webView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ProgressBar bar;
    private AdView adView;
    private static final String TAG = "adview: ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_area);
        setUpToolbar();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MobileAds.initialize(this, getString(R.string.appid) );


        MobileAds.initialize(this, getString(R.string.appid) );
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        adView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                Log.d(TAG,"Ad failed to Load");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG,"Ad closed");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG,"Ad is Clicked");
            }

            @Override
            public void onAdLoaded() {
                Log.d(TAG,"Ad Loaded");
            }
        });




        webView = (WebView) findViewById(R.id.wb);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        webView.setWebViewClient(new myWebclient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://codingatharva.blogspot.com/p/blog-page_3.html");
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent home = new Intent(CodingArea.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.nav_ther:
                        Intent therr = new Intent(CodingArea.this, TheoryPart.class);
                        startActivity(therr);
                        break;
                    case R.id.nav_prog:
                        Intent progg = new Intent(CodingArea.this, WebPage.class);
                        startActivity(progg);
                        break;
                    case R.id.nav_privacy:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://privacypoliciesplay.blogspot.com/2018/09/privacy-policy-of-official-app.html"));
                        startActivity(intent1);
                        break;
                    case R.id.nav_contact:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://codingatharva.blogspot.com/p/contact-us.html"));
                        startActivity(intent2);
                        break;
                    case R.id.nav_code:
                        Intent code = new Intent(CodingArea.this, CodingArea.class);
                        startActivity(code);
                        break;
                    case R.id.nav_about:
                        Intent intent3 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://codingatharva.blogspot.com/p/about-us.html"));
                        startActivity(intent3);
                        break;
                    case R.id.nav_text:
                        Intent speech = new Intent(CodingArea.this, Text.class);
                        startActivity(speech);
                        break;
                    case R.id.nav_disc:
                        Intent disc = new Intent(CodingArea.this,Discuss.class);
                        startActivity(disc);
                        break;
                }
                return false;
            }
        });


    }

    public class myWebclient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if ( Uri.parse(url).getHost().equals("codingatharva.blogspot.com") || Uri.parse(url).getHost().equals("www.google.com")  ) {
                view.loadUrl(url);
                bar.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, url);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return false;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            bar.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.CodingString, R.string.CodingString);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("Coding Area");
        actionBarDrawerToggle.syncState();
    }

}
