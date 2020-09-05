package com.github.goldfish.zamad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ZamAd implements ClickListener {
    private Context  context;
    private static String ZAM_ADS_KEY="ZamAds";
    private int click_count = 0;
    //private static int min_click = 1;
    private static int max_click = 3;
    private boolean isad_disabled=false;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ZamAd(Context context) {
        this.context=context;
        prefs = this.context.getSharedPreferences(ZAM_ADS_KEY,0);
        editor = prefs.edit();
    }

    public void initialize(){
        click_count = prefs.getInt("click_count",click_count);
        isad_disabled = prefs.getBoolean("disable_ads",isad_disabled);
    }

    @Override
    public void onClicked(int count, @Nullable AdView adView) {
        click_count +=1;
        editor.putInt("click_count",count).apply();
        if(adView!=null)
        check_ClickThreshold(adView);

    }


    @Override
    public void onClicked(int count) {
        click_count +=1;
        editor.putInt("click_count",count).apply();
            check_ClickThreshold();
    }

    @Override
    public void onDestroyClickReset() {
        if(click_count<max_click){
            editor.putInt("click_count",0).apply();
        }

    }

    public int getClickedCount(){
        return click_count;
    }


    public void check_ClickThreshold(AdView adView){
        if(getClickedCount()>max_click){
            if(adView!=null){
                adView.destroy();

            }
            editor.putBoolean("disable_ads", true).apply();
        }
    }

    public void check_ClickThreshold(){
        if(getClickedCount()>max_click){
            editor.putBoolean("disable_ads", true).apply();
        }
    }

    public boolean isAdClosed(){
        return isad_disabled;
    }



}
