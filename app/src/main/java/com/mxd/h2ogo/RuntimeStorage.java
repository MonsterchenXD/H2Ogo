package com.mxd.h2ogo;

import android.util.Log;

import java.util.List;

public class RuntimeStorage {

    private static final String TAG = "RuntimeStorage";

    private List<Swimmer> listSwimmers;
    private List<Team> listTeams;

    public RuntimeStorage() {
        generateSwimmers();
        generateTeams();
    }

    /**
     * Add
     */
    public Swimmer addSwimmer(String name) {
        Swimmer swimmer = new Swimmer(StorageManager.generateStorageIdSwimmer(), name);
        listSwimmers.add(swimmer);
        return swimmer;
    }

    public Team addTeam(String name) {
        Team team = new Team(StorageManager.generateStorageIdTeam(), name);
        listTeams.add(team);
        return team;
    }

    /**
     * Remove
     */
    public void removeSwimmer(Swimmer swimmer) {
        if (listSwimmers.contains(swimmer))
            listSwimmers.remove(swimmer);
        else
            Log.e(TAG, "Swimmer '" + swimmer.toString() + "' not found");
    }

    public void removeTeam(Team team) {
        if (listTeams.contains(team))
            listTeams.remove(team);
        else
            Log.e(TAG, "Team '" + team.toString() + "' not found");
    }

    /**
     * Get
     */
    public List<Swimmer> getListSwimmers() {
        return listSwimmers;
    }

    public List<Team> getListTeams() {
        return listTeams;
    }

    /**
     * Generate Lists
     */
    private void generateSwimmers() {
        listSwimmers = StorageManager.readAllStoredSwimmers();
        log(listSwimmers);
    }

    private void generateTeams() {
        listTeams = StorageManager.readAllStoredTeams();
        log(listTeams);
    }

    /**
     * Save
     */
    public void save() {
        StorageManager.saveAllSwimmers(listSwimmers);
        StorageManager.saveAllTeams(listTeams);
    }

    /**
     * Log
     */
    public <T extends DataSet> void log(List<T> listDataSet) {
        String classname = listDataSet.isEmpty() ? "-" : listDataSet.get(0).getClass().getSimpleName();
        Log.w(TAG, "Log list<" + classname + ">:");
        for (DataSet dataSet : listDataSet)
            dataSet.log();
    }

}
