package com.mxd.h2ogo;

import android.content.res.AssetManager;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public abstract class mxd {

    public static MainActivity ma;
    public static final String tr = "&&";

    //////////
    //String//
    //////////

    public static String sAddAtEnd(String container, String s, boolean allowmultiple) {
        if ((!allowmultiple) && sContains(container, s)) {
            return container;
        }
        if ((!container.contentEquals(""))) {
            container += tr;
        }
        container += s;
        return container;
    }

    public static String sAddAt(String container, String s, int position, boolean allowmultiple) {
        if (position >= sGetLength(container)) {
            return sAddAtEnd(container, s, allowmultiple);
        }
        if ((!allowmultiple) && sContains(container, s)) {
            return container;
        }
        String[] ss = container.split(tr);
        container = "";
        for (int i = 0; i < ss.length; i++) {
            if (!container.contentEquals("")) {
                container += tr;
            }
            if (i == position) {
                container += s + tr;
            }
            container += ss[i];
        }
        return container;
    }

    public static int sGetLength(String container) {
        return container.split(tr).length;
    }

    public static String sRemove(String container, String s) {
        if (!container.contains(s)) {
            return container;
        }
        String[] ss = container.split(tr);
        container = "";
        for (int i = 0; i < ss.length; i++) {
            if (!ss[i].contentEquals(s)) {
                if (!container.contentEquals("")) {
                    container += tr;
                }
                container += ss[i];
            }
        }
        return container;
    }

    public static String sRelocate(String container, String s, int position) {
        container = sRemove(container, s);
        container = sAddAt(container, s, position, false);
        return container;
    }

    public static boolean sContains(String container, String s) {
        String[] ss = container.split(tr);
        for (int i = 0; i < ss.length; i++) {
            if (ss[i].contentEquals(s)) {
                return true;
            }
        }
        return false;
    }

    public static String sBuild(String[] ss) {
        String s = "";
        for (int i = 0; i < ss.length; i++) {
            if (!s.contentEquals("")) {
                s += tr;
            }
            s += ss[i];
        }
        return s;
    }

    public static String sBuild(String[] ss, String trr) {
        String s = "";
        for (int i = 0; i < ss.length; i++) {
            if (!s.contentEquals("")) {
                s += trr;
            }
            s += ss[i];
        }
        return s;
    }

    ////////
    //List//
    ////////

    public static String ListToString(List<String> list, String trr) {
        String s = "";
        while (list.size() > 0) {
            if (!s.contentEquals(""))
                s += trr;
            s += list.remove(0);
        }
        return s;
    }

    public static List<String> StringToList(String s, String trr) {
        List<String> list = new ArrayList<String>();
        if (s.contentEquals(""))
            return list;
        String[] ss = s.split(trr);
        for (int i = 0; i < ss.length; i++) {
            list.add(ss[i]);
        }
        return list;
    }


    ///////////////
    //FileStreams//
    ///////////////

    public static void Save(String filename, String outputString) {

        try {
            FileOutputStream outputStream = ma.openFileOutput(filename, ma.MODE_PRIVATE);
            outputStream.write(outputString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = ma.openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            r.close();
            inputStream.close();
            Log.d("File", "File contents: " + total);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String Read(String filename) {
        try {
            FileInputStream fis = ma.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String s = sb.toString().split("\n")[0];
            return s;
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }

    }

    ////////////////////
    //External Storage//
    ////////////////////

    public static void WriteExternal(String path, String filename, String data) {
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
    }

    /////////
    //Print//
    /////////

    public static void PrintLong(String s) {
        Toast.makeText(ma, s, Toast.LENGTH_LONG).show();
    }

    public static void PrintShort(String s) {
        Toast.makeText(ma, s, Toast.LENGTH_SHORT).show();
    }

    ////////////////
    //Softkeyboard//
    ////////////////

    public static void ShowSoftkeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) ma.getSystemService(ma.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void HideSoftkeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) ma.getSystemService(ma.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    ///////////////
    //InputStream//
    ///////////////

    public static String getLicenses() {
        String message = "";
        try {
            AssetManager am = ma.getAssets();
            InputStream is = am.open("license.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                if (receiveString.equals("")) {
                    stringBuilder.append(System.getProperty("line.separator"));
                } else {
                    stringBuilder.append(receiveString);
                }
            }
            is.close();
            message = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
