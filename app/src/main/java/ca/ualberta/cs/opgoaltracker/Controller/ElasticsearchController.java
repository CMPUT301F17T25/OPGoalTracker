/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.opgoaltracker.models.Admin;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Mingwei Li on 2017/11/22.
 */

public class ElasticsearchController {
    private static JestDroidClient client;

    /**
     * Add participants to type "participant"
     * Need to GetParticipantsTask to check if the participant exists before adding
     */
    public static class AddParticipantsTask extends AsyncTask<Participant, Void, Void> {

        @Override
        protected Void doInBackground(Participant... participants) {
            verifySettings();

            for (Participant participant : participants) {
                Index index = new Index.Builder(participant).index("team25").type("participant").id(participant.getId()).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        result.getId();
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the Participant");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the participants");
                }

            }
            return null;
        }
    }

    /**
     * Search participants matches query
     * @return participants ArrayList<Participant>
     * if there is no matches, return an empty ArrayList
     */
    public static class GetParticipantsTask extends AsyncTask<String, Void, ArrayList<Participant>> {
        @Override
        protected ArrayList<Participant> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Participant> participants = new ArrayList<Participant>();

            Search search = new Search.Builder(search_parameters[0]).addIndex("team25").addType("participant").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Participant> foundParticipant = result.getSourceAsObjectList(Participant.class);
                    participants.addAll(foundParticipant);
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return participants;
        }
    }

    /**
     * Add admins to type "admin"
     * Need to GetAdminsTask to check if the admin exists before adding
     */
    public static class AddAdminsTask extends AsyncTask<Admin, Void, Void> {

        @Override
        protected Void doInBackground(Admin... admins) {
            verifySettings();

            for (Admin admin : admins) {
                Index index = new Index.Builder(admin).index("team25").type("admin").id(admin.getId()).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        result.getId();
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the Admin");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the admins");
                }

            }
            return null;
        }
    }

    /**
     * Search admins matches query
     * @return admins ArrayList<Admin>
     * if there is no matches, return an empty ArrayList
     */
    public static class GetAdminsTask extends AsyncTask<String, Void, ArrayList<Admin>> {
        @Override
        protected ArrayList<Admin> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Admin> admins = new ArrayList<Admin>();

            Search search = new Search.Builder(search_parameters[0]).addIndex("team25").addType("admin").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Admin> foundAdmin = result.getSourceAsObjectList(Admin.class);
                    admins.addAll(foundAdmin);
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return admins;
        }
    }

    /**
     * Add habits to type "habit"
     * Set the habit added with id which was assigned by Elasticsearch
     */
    public static class AddHabitsTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index("team25").type("habit").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        habit.setId(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habit");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }

            }
            return null;
        }
    }

    /**
     * Search habits matches query
     * @return habits ArrayList<Habit>
     * if there is no matches, return an empty ArrayList
     */
    public static class GetHabitsTask extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Habit> habits = new ArrayList<Habit>();

            Search search = new Search.Builder(search_parameters[0]).addIndex("team25").addType("habit").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Habit> foundHabit = result.getSourceAsObjectList(Habit.class);
                    habits.addAll(foundHabit);
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return habits;
        }
    }

    /**
     * Update specific habits
     */
    public static class UpdateHabitsTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index("team25").type("habit").id(habit.getId()).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        result.getId();
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habit");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }

            }
            return null;
        }
    }

    /**
     * Add habitEvents to type "habitevent"
     * Set the habitEvent added with id which was assigned by Elasticsearch
     */
    public static class AddHabitEventsTask extends AsyncTask<HabitEvent, Void, Void> {

        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            verifySettings();

            for (HabitEvent habitEvent : habitEvents) {
                Index index = new Index.Builder(habitEvent).index("team25").type("habitevent").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        habitEvent.setId(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habitEvent");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habitEvents");
                }

            }
            return null;
        }
    }

    /**
     * Search habitEvents matches query
     * @return habitEvents ArrayList<HabitEvent>
     * if there is no matches, return an empty ArrayList
     */
    public static class GetHabitEventsTask extends AsyncTask<String, Void, ArrayList<HabitEvent>> {
        @Override
        protected ArrayList<HabitEvent> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();

            Search search = new Search.Builder(search_parameters[0]).addIndex("team25").addType("habitevent").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<HabitEvent> foundHabitEvent = result.getSourceAsObjectList(HabitEvent.class);
                    habitEvents.addAll(foundHabitEvent);
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return habitEvents;
        }
    }

    /**
     * Update specific habits
     */
    public static class UpdateHabitEventsTask extends AsyncTask<HabitEvent, Void, Void> {

        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            verifySettings();

            for (HabitEvent habitEvent : habitEvents) {
                Index index = new Index.Builder(habitEvent).index("team25").type("habit").id(habitEvent.getId()).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        result.getId();
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habitEvent");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habitEvents");
                }

            }
            return null;
        }
    }



    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}
