package com.mxd.h2ogo;

import java.util.ArrayList;
import java.util.List;

public abstract class SerializeManager {

    private static final String TAG = "SerializeManager";
    protected static final String TR_STORAGE = "&&";

    public static Swimmer DeserializeToSwimmer(String serData) {
        String[] data = serData.split(TR_STORAGE);
        String storageId = data[0];
        String name = data[1];
        return new Swimmer(storageId, name);
    }

    /*public static String ZipSwimmer(Swimmer swimmer) {
        List<String> listData = new ArrayList<>();
        listData.add(swimmer.storageId);
        listData.add(swimmer.name);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : listData) {
            stringBuilder.append(TR_STORAGE);
            stringBuilder.append(s);
        }
        return stringBuilder.substring(TR_STORAGE.length());
    }*/

    public static Team DeserializeToTeam(String serData) {
        String[] data = serData.split(TR_STORAGE);
        String storageId = data[0];
        String name = data[1];
        return new Team(storageId, name);
    }

    public static <T extends Swimmer> String Serialize(T swimmer){
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

    public static <T extends Team> String Serialize(T team){
        List<String> listData = new ArrayList<>();
        listData.add(team.storageId);
        listData.add(team.name);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : listData) {
            stringBuilder.append(TR_STORAGE);
            stringBuilder.append(s);
        }
        return stringBuilder.substring(TR_STORAGE.length());
    }

}
