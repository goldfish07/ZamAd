package com.goldfish07.zamads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.zamad.ZamAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private ZamAd zamAd;
    private Button loadadBtn;
    private TextView adslog;
    private boolean isadloaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadadBtn = findViewById(R.id.loadadsBtn);
        adslog = findViewById(R.id.adslogging);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        zamAd = new ZamAd(this);
        zamAd.initialize();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        loadadBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!mInterstitialAd.isLoading())
            if(isadloaded){
                mInterstitialAd.show();
            }else{
                loadads();
            }
        }
    };

    public void loadads(){
        if(mInterstitialAd.isLoading())
            adslog.setText("Loading...");

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adslog.setText("");
                loadadBtn.setText("LOAD ADS");
                isadloaded=false;
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }


            @Override
            public void onAdOpened() {
                super.onAdOpened();
                zamAd.onClicked(1,null);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adslog.setText("Loaded !");
                loadadBtn.setText("SHOW ADS");
                isadloaded=true;


            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                adslog.setText("");
                loadadBtn.setText("LOAD ADS");
                isadloaded=false;

            }

        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mInterstitialAd.isLoaded())
        adslog.setText("Loaded!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zamAd.onDestroyClickReset();
    }
}
