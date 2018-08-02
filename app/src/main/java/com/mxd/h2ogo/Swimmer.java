package com.mxd.h2ogo;

import android.util.Log;

public class Swimmer extends DataSet {

    private static final String TAG = "Swimmer";

    String name;

    public Swimmer(String storageId, String name) {
        this.storageId = storageId;
        this.name = name;
    }

    @Override
    public String serialize() {
        return SerializeManager.serialize(this);
    }

    @Override
    public void log() {
        Log.w(TAG, toString());
    }

    @Override
    public String toString() {
        return storageId + ", " + name;
    }

}
