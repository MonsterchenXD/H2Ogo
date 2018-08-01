package com.mxd.h2ogo;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class StorageManager extends ZipManager{

    private static final String PATH_SWIMMERS = "H2Ogo/Swimmers";
    private static final String TAG = "StorageManager";

    public static List<Swimmer> ReadAllStoredSwimmers() {
        List<Swimmer> listSwimmers = new ArrayList<>();
        List<String> listData = getZipdataOfPath(PATH_SWIMMERS);
        for (String data : listData)
            listSwimmers.add(UnzipSwimmer(data));
        return listSwimmers;
    }

    public static void SaveAllSwimmers(List<Swimmer> listSwimmers) {
        List<String> listZipdata = new ArrayList<>();
        for (Swimmer swimmer : listSwimmers)
            listZipdata.add(ZipSwimmer(swimmer));
        setZipdataOfPath(PATH_SWIMMERS, listZipdata);
    }

    private static List<String> getZipdataOfPath(String path) {
        List<String> listStringsData = new ArrayList<>();
        File filepath = new File(Environment.getExternalStorageDirectory(), path);
        filepath.mkdirs();
        File[] files = filepath.listFiles();
        for (File file : files) {
            String storageId = file.toString().substring(file.toString().lastIndexOf('/') + 1);
            String data = storageId + TR_STORAGE + getZipdataOfFile(file);
            listStringsData.add(data);
        }
        return listStringsData;
    }

    private static String getZipdataOfFile(File file) {
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

    private static void setZipdataOfPath(String path, List<String> listData) {
        File filepath = new File(Environment.getExternalStorageDirectory(), path);
        filepath.mkdirs();
        for (int i = 0; i < listData.size(); i++) {
            String data = listData.get(i);
            String storageId = data.split(TR_STORAGE)[0];
            data = data.substring(storageId.length() + TR_STORAGE.length());
            File file = new File(filepath, storageId);
            setZipdataOfFile(file, data);
        }
    }

    private static void setZipdataOfFile(File file, String data) {
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void WriteExternal(String path, String filename, String data) {
        try {
            String[] paths = path.split("/");
            File filepath = Environment.getExternalStorageDirectory();
            for (int i = 0; i < paths.length; i++) {
                filepath = new File(filepath, paths[i]);
                if (!filepath.exists()) {
                    filepath.mkdirs();
                }
            }
            File file = new File(filepath, filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ReadExternal(String path, String filename) {
        String data = "";
        try {
            File filepath = new File(Environment.getExternalStorageDirectory(), path);
            if (filepath.exists()) {
                File file = new File(filepath, filename);
                FileInputStream fis = new FileInputStream(file);
                StringBuffer fileContent = new StringBuffer("");
                int n;
                byte[] buffer = new byte[1024];

                while ((n = fis.read(buffer)) != -1) {
                    fileContent.append(new String(buffer, 0, n));
                }
                data = fileContent.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }*/

}