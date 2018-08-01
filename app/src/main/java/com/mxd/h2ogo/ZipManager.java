package com.mxd.h2ogo;

import java.util.ArrayList;
import java.util.List;

public abstract class ZipManager {

    private static final String TAG = "ZipManager";
    protected static final String TR_STORAGE = "&&";

    public static Swimmer UnzipSwimmer(String zipData) {
        String[] data = zipData.split(TR_STORAGE);
        String storageId = data[0];
        String name = data[1];
        return new Swimmer(storageId, name);
    }

    public static String ZipSwimmer(Swimmer swimmer) {
        List<String> listData = new ArrayList<>();
        listData.add(swimmer.storageId);
        listData.add(swimmer.name);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : listData) {
            stringBuilder.append(TR_STORAGE);
            stringBuilder.append(s);
        }
        return stringBuilder.substring(TR_STORAGE.length());
    }

    public static Team UnzipTeam(String zipData) {
        String[] data = zipData.split(TR_STORAGE);
        String storageId = data[0];
        String name = data[1];
        return new Team(storageId, name);
    }

}
