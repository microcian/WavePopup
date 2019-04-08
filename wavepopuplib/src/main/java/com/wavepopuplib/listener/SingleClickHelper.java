package com.wavepopuplib.listener;

import android.os.SystemClock;

class SingleClickHelper {

    private long preClickTime;


    boolean clickEnable() {
        long clickTime = SystemClock.elapsedRealtime();
        long l_CLICK_INTERVAL = 400;
        if (clickTime - preClickTime > l_CLICK_INTERVAL) {
            preClickTime = clickTime;
            return true;
        }
        return false;
    }
}
