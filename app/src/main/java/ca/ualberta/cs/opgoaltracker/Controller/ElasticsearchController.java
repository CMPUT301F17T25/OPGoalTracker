/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.Controller;

import android.content.Intent;
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
import ca.ualberta.cs.opgoaltracker.models.Restriction;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Mingwei Li on 2017/11/22.
 */

public class ElasticsearchController {
    private static JestDroidClient client;
    
    private static final String INDEX = "team25";
    private static final String TYPE_PARTICIPANT = "participant";
    private static final String TYPE_ADMIN = "admin";
    private static final String TYPE_HABIT = "habit";
    private static final String TYPE_HABITEVENT = "habitevent";
    private static final String TYPE_RESTRICTION = "restriction";
    private static final String ID_RESTRICTION = "restriction";
    private static final long SLEEPTIME = 3000;

    /**
     * Add participants to type TYPE_PARTICIPANT
     * Need to GetParticipantsTask to check if the participant exists before adding
     */
    public static class AddParticipantsTask extends AsyncTask<Participant, Void, Void> {

        @Override
        protected Void doInBackground(Participant... participants) {
            verifySettings();

            for (Participant participant : participants) {
                Index index = new Index.Builder(participant).index(INDEX).type(TYPE_PARTICIPANT).id(participant.getId()).build();

                DocumentResult result = null;

                do { // if upload failed, thread will retry after several seconds
                    try {
                        // where is the client?
                        result = client.execute(index);

                        if(result.isSucceeded())
                        {
                            result.getId();
                        } else {
                            Log.i("Error", "Elasticsearch was not able to add the Participant");
                        }
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the participants");

                        try {
                            Thread.sleep(SLEEPTIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } while (result == null);
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

            Search search = new Search.Builder(search_parameters[0]).addIndex(INDEX).addType(TYPE_PARTICIPANT).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Participant> foundParticipant = result.getSourceAsObjectList(Participant.class);
                    participants.addAll(foundParticipant);

                    return participants;
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");

                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                return null;
            }
        }
    }

    /**
     * Add admins to type TYPE_ADMIN
     * Need to GetAdminsTask to check if the admin exists before adding
     */
    public static class AddAdminsTask extends AsyncTask<Admin, Void, Void> {

        @Override
        protected Void doInBackground(Admin... admins) {
            verifySettings();

            for (Admin admin : admins) {
                Index index = new Index.Builder(admin).index(INDEX).type(TYPE_ADMIN).id(admin.getId()).build();

                DocumentResult result = null;

                do { // if upload failed, thread will retry after several seconds
                    try {
                        // where is the client?
                        result = client.execute(index);

                        if(result.isSucceeded())
                        {
                            result.getId();
                        } else {
                            Log.i("Error", "Elasticsearch was not able to add the Admin");
                        }
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the admins");

                        try {
                            Thread.sleep(SLEEPTIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } while (result == null);
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

            Search search = new Search.Builder(search_parameters[0]).addIndex(INDEX).addType(TYPE_ADMIN).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Admin> foundAdmin = result.getSourceAsObjectList(Admin.class);
                    admins.addAll(foundAdmin);

                    return admins;
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");

                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                return null;
            }
        }
    }

    /**
     * Delete admins matches query
     */
    public static class DeleteAdminsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... delete_parameters) {
            verifySettings();

            DeleteByQuery delete = new DeleteByQuery.Builder(delete_parameters[0]).addIndex(INDEX).addType(TYPE_ADMIN).build();

            JestResult result = null;

            do { // if upload failed, thread will retry after 30 seconds
                try {
                    result = client.execute(delete);
                    if (result.isSucceeded())
                    {
                        Log.i("Succeed", "Successfully deleted Admin objects.");
                    } else {
                        Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            } while (result == null);

            return null;
        }
    }

    /**
     * Add or update habits to type TYPE_HABIT
     */
    public static class AddHabitsTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index(INDEX).type(TYPE_HABIT).id(habit.getId()).build();

                DocumentResult result = null;

                do { // if upload failed, thread will retry after several seconds
                    try {
                        // where is the client?
                        result = client.execute(index);

                        if(result.isSucceeded())
                        {
                            result.getId();
                        } else {
                            Log.i("Error", "Elasticsearch was not able to add the habit");
                        }
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the habits");

                        try {
                            Thread.sleep(SLEEPTIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } while (result == null);
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

            Search search = new Search.Builder(search_parameters[0]).addIndex(INDEX).addType(TYPE_HABIT).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Habit> foundHabit = result.getSourceAsObjectList(Habit.class);
                    habits.addAll(foundHabit);

                    return habits;
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");

                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                return null;
            }
        }
    }

    /**
     * Delete habits matches query
     */
    public static class DeleteHabitsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... delete_parameters) {
            verifySettings();

            DeleteByQuery delete = new DeleteByQuery.Builder(delete_parameters[0]).addIndex(INDEX).addType(TYPE_HABIT).build();

            JestResult result = null;

            do { // if upload failed, thread will retry after several seconds
                try {
                    result = client.execute(delete);
                    if (result.isSucceeded())
                    {
                        Log.i("Succeed", "Successfully deleted Habit objects.");
                    } else {
                        Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            } while (result == null);

            return null;
        }
    }

    /**
     * Add or update habitEvents to type TYPE_HABITEVENT
     */
    public static class AddHabitEventsTask extends AsyncTask<HabitEvent, Void, Void> {

        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            verifySettings();

            for (HabitEvent habitEvent : habitEvents) {
                Index index = new Index.Builder(habitEvent).index(INDEX).type(TYPE_HABITEVENT).id(habitEvent.getId()).build();

                DocumentResult result = null;

                do { // if upload failed, thread will retry after several seconds
                    try {
                        // where is the client?
                        result = client.execute(index);

                        if(result.isSucceeded())
                        {
                            result.getId();
                        } else {
                            Log.i("Error", "Elasticsearch was not able to add the habitEvent");
                        }
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the habitEvents");

                        try {
                            Thread.sleep(SLEEPTIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } while (result == null);
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

            Search search = new Search.Builder(search_parameters[0]).addIndex(INDEX).addType(TYPE_HABITEVENT).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<HabitEvent> foundHabitEvent = result.getSourceAsObjectList(HabitEvent.class);
                    habitEvents.addAll(foundHabitEvent);

                    return habitEvents;
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");

                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                return null;
            }
        }
    }

    /**
     * Delete habitEvents matches query
     */
    public static class DeleteHabitEventsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... delete_parameters) {
            verifySettings();

            DeleteByQuery delete = new DeleteByQuery.Builder(delete_parameters[0]).addIndex(INDEX).addType(TYPE_HABITEVENT).build();

            JestResult result = null;

            do { // if upload failed, thread will retry after 30 seconds
                try {
                    result = client.execute(delete);
                    if (result.isSucceeded())
                    {
                        Log.i("Succeed", "Successfully deleted HabitEvent objects.");
                    } else {
                        Log.i("Error", "Something went wrong when we tried to communicate with the server.");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            } while (result == null);

            return null;
        }
    }


    /**
     * Add or update restriction to type TYPE_RESTRICTION
     */
    public static class AddRestrictionTask extends AsyncTask<Restriction, Void, Void> {

        @Override
        protected Void doInBackground(Restriction... restrictions) {
            verifySettings();

            for (Restriction restriction : restrictions) {
                Index index = new Index.Builder(restriction).index(INDEX).type(TYPE_RESTRICTION).id(ID_RESTRICTION).build();

                DocumentResult result = null;

                do { // if upload failed, thread will retry after several seconds
                    try {
                        // where is the client?
                        result = client.execute(index);

                        if(result.isSucceeded())
                        {
                            result.getId();
                        } else {
                            Log.i("Error", "Elasticsearch was not able to add the restriction");
                        }
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the restrictions");

                        try {
                            Thread.sleep(SLEEPTIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } while (result == null);
            }
            return null;
        }
    }

    /**
     * Search habitEvents matches query
     * @return habitEvents ArrayList<HabitEvent>
     * if there is no matches, return an empty ArrayList
     */
    public static class GetRestrictionTask extends AsyncTask<Void, Void, ArrayList<Restriction>> {
        @Override
        protected ArrayList<Restriction> doInBackground(Void... search_parameters) {
            verifySettings();

            ArrayList<Restriction> restrictions = new ArrayList<Restriction>();

            String query = "{\n" +
                    "	\"query\": {\n" +
                    "		\"term\": {\"_id\":\"" + ID_RESTRICTION + "\"}\n" +
                    "	}\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex(INDEX).addType(TYPE_RESTRICTION).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded())
                {
                    List<Restriction> foundRestriction = result.getSourceAsObjectList(Restriction.class);
                    restrictions.addAll(foundRestriction);

                    return restrictions;
                } else {
                    Log.i("Error", "Something went wrong when we tried to communicate with the server.");

                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

                return null;
            }
        }
    }





    public static void verifySettings() {
        if (client == null) {
//            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("https://5b3c205796b755b5db6f9b28b41fa441.us-east-1.aws.found.io:9243");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
