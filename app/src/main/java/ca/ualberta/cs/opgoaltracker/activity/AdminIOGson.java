package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.models.Admin;

/**
 * Created by donglin3 on 11/11/17.
 */

public class AdminIOGson {

    /**
     * Load Habit objects from all saved json files.
     * @return list of Habit objects.
     */
    public Admin loadAdminFromFile(String adminID, Context context) {
        Admin adminObj;

        try {
            FileInputStream fis = context.openFileInput(jsonFileName(adminID));
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            adminObj = gson.fromJson(in, Admin.class);
        } catch (FileNotFoundException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        }

        return adminObj;
    }

    public ArrayList<Admin> loadAllAdminFromFiles(ArrayList<String> adminIdList, Context context){
        ArrayList<Admin> adminList = new ArrayList<>();

        // TODO may need to start i from 1 instead of 0
        for(int i = 0; i < adminIdList.size(); i++) {
            adminList.add(this.loadAdminFromFile(adminIdList.get(i), context));
        }

        return adminList;
    }

    public void saveAdminInFile(Admin admin, Context context) {
        String fileName = jsonFileName(admin.getId());
        try {
            // TODO may need to use fos to write directly
            FileOutputStream fos = context.openFileOutput(fileName, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(admin, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public String jsonFileName(String adminID) {
        return adminID + ".json";
    }

    public void deleteFile(String adminID, Context context) {
        String fileName = jsonFileName(adminID);
        context.deleteFile(fileName);
    }
}