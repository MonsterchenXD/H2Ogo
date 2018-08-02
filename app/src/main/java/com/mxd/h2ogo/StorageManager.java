package com.mxd.h2ogo;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class StorageManager extends SerializeManager {

    private static final String PATH_SWIMMERS = "H2Ogo/Swimmers";
    private static final String PATH_TEAMS = "H2Ogo/Teams";
    private static final String STORAGEIDPREFIX_SWIMMER = "@00+";
    private static final String STORAGEIDPREFIX_TEAM = "@01+";
    private static final int STORAGEID_LENGTH = 4;
    private static final String TAG = "StorageManager";

    /**
     * Read
     */
    public static List<Swimmer> readAllStoredSwimmers() {
        List<Swimmer> listSwimmers = new ArrayList<>();
        List<String> listData = getSerdataOfPath(PATH_SWIMMERS);
        for (String data : listData)
            listSwimmers.add(deserializeToSwimmer(data));
        return listSwimmers;
    }

    public static List<Team> readAllStoredTeams() {
        List<Team> listTeams = new ArrayList<>();
        List<String> listData = getSerdataOfPath(PATH_TEAMS);
        for (String data : listData)
            listTeams.add(deserializeToTeam(data));
        return listTeams;
    }

    /**
     * Save
     */
    public static void saveAllSwimmers(List<Swimmer> listSwimmers) {
        List<String> listSerdata = new ArrayList<>();
        for (Swimmer swimmer : listSwimmers)
            listSerdata.add(swimmer.serialize());
        setSerdataOfPath(PATH_SWIMMERS, listSerdata);
    }

    public static void saveAllTeams(List<Team> listTeams) {
        List<String> listSerdata = new ArrayList<>();
        for (Team team : listTeams)
            listSerdata.add(team.serialize());
        setSerdataOfPath(PATH_TEAMS, listSerdata);
    }

    /**
     * get Serdata
     */
    private static List<String> getSerdataOfPath(String path) {
        List<String> listStringsData = new ArrayList<>();
        File filepath = new File(Environment.getExternalStorageDirectory(), path);
        filepath.mkdirs();
        File[] files = filepath.listFiles();
        for (File file : files) {
            String storageId = file.toString().substring(file.toString().lastIndexOf('/') + 1);
            String data = storageId + TR_STORAGE + getSerdataOfFile(file);
            listStringsData.add(data);
        }
        return listStringsData;
    }

    private static String getSerdataOfFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            StringBuilder fileContent = new StringBuilder();
            int n;
            byte[] buffer = new byte[1024];
            while ((n = fis.read(buffer)) != -1)
                fileContent.append(new String(buffer, 0, n));
            return fileContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * set Serdata
     */
    private static void setSerdataOfPath(String path, List<String> listData) {
        File filepath = new File(Environment.getExternalStorageDirectory(), path);
        filepath.mkdirs();
        deleteAllFilesInDir(filepath);
        for (int i = 0; i < listData.size(); i++) {
            String data = listData.get(i);
            String storageId = data.split(TR_STORAGE)[0];
            data = data.substring(storageId.length() + TR_STORAGE.length());
            File file = new File(filepath, storageId);
            setSerdataOfFile(file, data);
        }
    }

    private static void deleteAllFilesInDir(File filepath) {
        String[] children = filepath.list();
        for (int i = 0; i < children.length; i++)
            new File(filepath, children[i]).delete();
    }

    private static void setSerdataOfFile(File file, String data) {
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * generate storageId
     */
    public static String generateStorageIdSwimmer() {
        return generateStorageId(MainActivity.ma.rs.getListSwimmers(), STORAGEIDPREFIX_SWIMMER);
    }

    public static String generateStorageIdTeam() {
        return generateStorageId(MainActivity.ma.rs.getListTeams(), STORAGEIDPREFIX_TEAM);
    }

    private static <T extends DataSet> String generateStorageId(List<T> listDataSet, String storageIdPrefix) {
        List<String> listOccIds = new ArrayList<>();
        for (DataSet dataSet : listDataSet) {
            listOccIds.add(dataSet.storageId);
        }
        for (int i = 0; i <= listDataSet.size(); i++) {
            String storageId = storageIdPrefix + buildStorageId(i);
            if (!listOccIds.contains(storageId)) {
                return storageId;
            }
        }
        Log.e(TAG, "Could not generate storageId");
        return "could not generate storageId";
    }

    private static String buildStorageId(int i) {
        StringBuilder storageId = new StringBuilder();
        storageId.append(i);
        while (storageId.length() < STORAGEID_LENGTH)
            storageId.insert(0, 0);
        return storageId.toString();
    }

}
