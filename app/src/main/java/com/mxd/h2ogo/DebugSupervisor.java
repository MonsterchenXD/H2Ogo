package com.mxd.h2ogo;

import android.util.Log;

public abstract class DebugSupervisor {

    private static final String TAG = "DabugSupervisor";
    private static long start;

    public static void StartMeasuring() {
        start = System.currentTimeMillis();
    }

    public static void StopMeasuring() {
        Log.w(TAG, "Time since start: " + (System.currentTimeMillis() - start));
    }

}
