package com.blogspot.codingatharva.diplomaincomputersciecne;

import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Text extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
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
        setContentView(R.layout.activity_text);
        setUpToolbar();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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


        mButtonSpeak = findViewById(R.id.button_speak);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.GERMAN);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent home = new Intent(Text.this,MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.nav_ther:
                        Intent therr = new Intent(Text.this,TheoryPart.class);
                        startActivity(therr);
                        break;
                    case R.id.nav_code:
                        Intent code = new Intent(Text.this,CodingArea.class);
                        startActivity(code);
                        break;
                    case R.id.nav_prog:
                        Intent progg = new Intent(Text.this,WebPage.class);
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
                    case R.id.nav_about:
                        Intent intent3 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://codingatharva.blogspot.com/p/about-us.html"));
                        startActivity(intent3);
                        break;
                    case R.id.nav_text:
                        Intent speech = new Intent(Text.this,Text.class);
                        startActivity(speech);
                        break;
                    case R.id.nav_disc:
                        Intent disc = new Intent(Text.this,Discuss.class);
                        startActivity(disc);
                        break;

                }
                return false;
            }
        });


    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    private void setUpToolbar()
    {
        drawerLayout =  findViewById(R.id.drawerLayout);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.CodingString,R.string.CodingString);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("Text to Speech");
        actionBarDrawerToggle.syncState();
    }
}