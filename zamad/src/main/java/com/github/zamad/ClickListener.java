package com.github.zamad;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdView;

public interface ClickListener {

    void onClicked(int count, @Nullable AdView adView);
    void onDestroyClickReset();
}
