package com.longx.intelligent.android.lib.longdialog.ui;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by LONG on 2026/6/4 at 上午2:51.
 */
public class UiThread {

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void run(Runnable r) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            handler.post(r);
        }
    }

    public static void runDelayed(Runnable r, long delayMillis) {
        handler.postDelayed(r, delayMillis);
    }

    public static void removeDelayed(Runnable r) {
        handler.removeCallbacks(r);
    }

    public static void removeAllDelayed() {
        handler.removeCallbacksAndMessages(null);
    }
}