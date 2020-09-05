package com.github.goldfish.zamad;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public interface ClickListener {

    void onClicked(int count, @Nullable AdView adView);
    void onClicked(int count);
    void onDestroyClickReset();
}
