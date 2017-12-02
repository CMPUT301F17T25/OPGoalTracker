/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;
import ca.ualberta.cs.opgoaltracker.models.Photograph;

/**
 * Created by song on 2017/11/23.
 */

public class RequestAdapter extends ArrayAdapter<ParticipantName> {
    private ArrayList<ParticipantName> requestList;
    private Participant currentUser;
    private ParticipantName newFollowerName;
    private Participant newFollower;
    private ArrayList<ParticipantName> targetFollowingList;
    private ArrayList<ParticipantName> followerList;
    private ArrayList<String> locationList;
    private int currentPosition;
    private Photograph photo;
    Context mContext;

    /**
     * Constructor for adapter
     * @param context
     * @param requestList
     */
    public RequestAdapter(Context context, ArrayList<ParticipantName> requestList,Participant currentUser) {
        super(context, R.layout.fragment_friend, requestList);
        this.requestList = requestList;
        this.currentUser = currentUser;
        this.mContext=context;
    }

    /**
     * getCount method
     * @return size of followingList
     */
    @Override
    public int getCount() {
        return requestList.size();
    }

    /**
     * getItem method
     * @param pos
     * @return the participant at selected location
     */
    @Override
    public ParticipantName getItem(int pos) {
        return requestList.get(pos);
    }

    /**
     * getView method, used to generate the view for RequestListFragment page
     * also define the onClick method for unfollow button.
     * @param position
     * @param convertView
     * @param parent
     * @return customView, view for FollowingListFragment
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.request_item,parent,false);
        newFollowerName = getItem(position);
        currentPosition = position;

        String query = "{\n" +
                "	\"query\": {\n" +
                "		\"term\": {\"_id\":\"" + newFollowerName.getId() + "\"}\n" +
                "	}\n" +
                "}";
        ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
        getParticipantsTask.execute(query);

        try {
            if (getParticipantsTask.get() == null) { // check if connected to server
                Toast.makeText(convertView.getContext(), "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
            }else if (getParticipantsTask.get().isEmpty() == false){
                newFollower = getParticipantsTask.get().get(0);
                targetFollowingList = newFollower.getFollowingList();
                photo = newFollower.getAvatar();
                followerList = currentUser.getFollowerList();
                locationList = newFollower.getLocation();
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the participant from the asyc object");
        }

        TextView userName = (TextView) customView.findViewById(R.id.userName);
        TextView location = (TextView) customView.findViewById(R.id.location);
        ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        Button accept = (Button) customView.findViewById(R.id.accept);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
                notifyDataSetChanged();
            }
        });

        userName.setText(newFollower.getId());
        if (photo!=null){
            picture.setImageBitmap(photo.getBitMap());
        }
        try {
            String cityName;
            Geocoder gcd = new Geocoder(convertView.getContext(), Locale.getDefault());
            if (locationList.get(0) != null && locationList.get(1)!=null){
                if (locationList.get(0) != null && locationList.get(1)!=null){
                    List<Address> addresses = gcd.getFromLocation(Double.parseDouble(locationList.get(0)),
                            Double.parseDouble(locationList.get(1)), 1);
                    if (addresses.size() > 0) {
                        cityName = addresses.get(0).getLocality();
                        location.setText(cityName);
                    }
                }
            }else{
                location.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customView;
    }

    public void acceptRequest(){
        requestList.remove(currentPosition);
        followerList.add(newFollowerName);
        targetFollowingList.add(new ParticipantName(currentUser));
        update(currentUser, newFollower);
    }

    public void update(Participant currentUser, Participant newFollower){
        ElasticsearchController.AddParticipantsTask addUsersTask1 = new ElasticsearchController.AddParticipantsTask();
        addUsersTask1.execute(currentUser);
        ElasticsearchController.AddParticipantsTask addUsersTask2 = new ElasticsearchController.AddParticipantsTask();
        addUsersTask2.execute(newFollower);
    }

}
