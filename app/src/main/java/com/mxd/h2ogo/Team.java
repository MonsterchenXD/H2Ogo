package com.mxd.h2ogo;

import android.util.Log;

public class Team extends DataSet {

    private static final String TAG = "Team";
    String name;

    public Team(String storageId, String name) {
        this.storageId = storageId;
        this.name = name;
    }

    public void Log() {
        Log.w(TAG, storageId + " " + name);
    }

}
