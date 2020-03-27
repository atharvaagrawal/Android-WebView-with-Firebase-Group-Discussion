package com.blogspot.codingatharva.diplomaincomputersciecne;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.blogspot.codingatharva.diplomaincomputersciecne.R.string.app_name;

public class NotesAtharva extends AppCompatActivity {

    private static final String TAG = "adview: ";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ProgressBar bar;
    WebView webView;


    String myCurrentURl = "";
    String myCurrentURLTitle = "";
    private InterstitialAd mInterstitialAd;
    private AdView adView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_atharva);
        setUpToolbar();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        MobileAds.initialize(this, getString(R.string.appid));
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Log.d(TAG, "Ad failed to Load");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "Ad closed");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "Ad is Clicked");
            }

            @Override
            public void onAdLoaded() {
                Log.d(TAG, "Ad Loaded");
            }
        });


        webView = (WebView) findViewById(R.id.wb);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        webView.setWebViewClient(new NotesAtharva.myWebclient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=155FGg43LPFkz6kPXyyCFRbwF7vnxQtKA");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5952137753275835/8251824106");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent home = new Intent(NotesAtharva.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.nav_ther:
                        Intent therr = new Intent(NotesAtharva.this, TheoryPart.class);
                        startActivity(therr);
                        break;
                    case R.id.nav_prog:
                        Intent progg = new Intent(NotesAtharva.this, WebPage.class);
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
                        Intent code = new Intent(NotesAtharva.this, CodingArea.class);
                        startActivity(code);
                        break;
                    case R.id.nav_about:
                        Intent intent3 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://codingatharva.blogspot.com/p/about-us.html"));
                        startActivity(intent3);
                        break;
                    case R.id.nav_text:
                        Intent speech = new Intent(NotesAtharva.this, Text.class);
                        startActivity(speech);
                        break;

                    case R.id.nav_disc:
                        Intent disc = new Intent(NotesAtharva.this, Discuss.class);
                        startActivity(disc);
                        break;

                    case R.id.nav_notes:
                        Intent note = new Intent(NotesAtharva.this, NotesAtharva.class);
                        startActivity(note);
                        break;
                }
                return false;
            }
        });

        Button c, cpp, cjava, ajava, dsu, dbms, js, html, os, se, st, mad, php, cpy, apy;

        c = findViewById(R.id.clang);
        cpp = findViewById(R.id.cpp);
        cjava = findViewById(R.id.cjava);
        ajava = findViewById(R.id.ajava);
        dsu = findViewById(R.id.dsu);
        dbms = findViewById(R.id.dbms);
        js = findViewById(R.id.js);
        html = findViewById(R.id.html);
        os = findViewById(R.id.os);
        se = findViewById(R.id.se);
        st = findViewById(R.id.st);
        mad = findViewById(R.id.mad);
        php = findViewById(R.id.php);
        cpy = findViewById(R.id.cpy);
        apy = findViewById(R.id.apy);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=14xw2kzlKARI1d4dlctYuWLrhSSGvImCI");
            }
        });

        cpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=15R4fXEVsh9QHCLh3ocfktckWRHEiulgx");
            }
        });


        cjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=155FGg43LPFkz6kPXyyCFRbwF7vnxQtKA");
            }
        });

        ajava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=15_oRZ8WIK6wn5Cf2AVDJXS7LoDvf5Uk3");
            }
        });

        dsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=15I7G6n_040FFuOP3kncl1JJxe-CfVh22");
            }
        });

        dbms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=158PSy3i89xd3jaB6g61R4OGqLdLAy12H");
            }
        });

        js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=14zBONOrZjavxm_zgisnsn9lm6bQGLihq");
            }
        });

        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=14zw02VAPJRiKwljabIs54NTzcX5nN0lG");
            }
        });

        os.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=15WMvh01yxFXo-bWqn9GA7k-sonItcVHn");
            }
        });

        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=15EX57yOA9QgUf45oZ_9cIAY6MR4VuWpT");
            }
        });

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=150SU39raLFrULEtiLIuBFUbjKd2UJ9Vv");
            }
        });

        mad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=16BToc58NB0TK_7AM97nSD_dIOgFqfx5i");
            }
        });

        php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=16HDTulMem3XBHGC4U1syk8qeB9hU6xay");
            }
        });

        cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl(" ");
            }
        });

        apy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                interstitialAdShow();
                webView.setWebViewClient(new NotesAtharva.myWebclient());
                webView.loadUrl(" ");
            }
        });

        webView.setVisibility(View.GONE);
    }

    public void interstitialAdShow() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
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

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, app_name, app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("Notes by Atharva Agrawal");
        actionBarDrawerToggle.syncState();
    }

    public class myWebclient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
            myCurrentURl = url;
            myCurrentURLTitle = view.getTitle();
            webView.loadUrl("javascript:(function() { " +
                    "document.querySelector('[role=\"toolbar\"]').remove();})()");
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            bar.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("codingatharva.blogspot.com") || Uri.parse(url).getHost().equals("www.google.com")) {
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


}
