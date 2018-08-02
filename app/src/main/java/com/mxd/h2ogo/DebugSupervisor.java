package com.mxd.h2ogo;

import android.util.Log;

public abstract class DebugSupervisor {

    private static final String TAG = "DabugSupervisor";
    private static long start;

    /**
     * Measuring
     */
    public static void startMeasuring() {
        start = System.currentTimeMillis();
    }

    public static void stopMeasuring() {
        Log.w(TAG, "Time since start: " + (System.currentTimeMillis() - start));
    }

}
