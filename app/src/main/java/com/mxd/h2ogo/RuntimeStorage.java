package com.mxd.h2ogo;

import android.util.Log;

import java.util.List;

public class RuntimeStorage {

    private static final String TAG = "RuntimeStorage";
    List<Swimmer> listSwimmer;
    List<Team> listTeams;

    public RuntimeStorage() {
        GenerateSwimmers();
        GenerateTeams();
    }

    private void GenerateSwimmers() {
        listSwimmer = StorageManager.ReadAllStoredSwimmers();
        Log(listSwimmer);
    }

    private void GenerateTeams() {
        listTeams = StorageManager.ReadAllStoredTeams();
        Log(listTeams);
    }

    public void Save() {
        StorageManager.SaveAllSwimmers(listSwimmer);
    }

    public <T extends DataSet> void Log(List<T> listDataSet){
        for (DataSet dataSet : listDataSet)
            dataSet.Log();
    }

}
