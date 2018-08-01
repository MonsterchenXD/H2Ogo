package com.mxd.h2ogo;

import android.util.Log;

public class Swimmer extends DataSet{

    private static final String TAG = "Swimmer";
    String name;

    public Swimmer(String storageId, String name) {
        this.storageId = storageId;
        this.name = name;
    }

    public void Log() {
        Log.w(TAG, storageId + " " + name);
    }

}
