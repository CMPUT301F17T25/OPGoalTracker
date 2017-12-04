/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import ca.ualberta.cs.opgoaltracker.models.User;

/**
 * Adapter for FollowingListFragment. It is used to inflate xml layout,
 * as well as handle buttons and on click events.
 *
 * @author song
 * @version 1.0
 *
 */
public class FollowingAdapter extends ArrayAdapter<ParticipantName> {
    private ArrayList<ParticipantName> followingList;
    private ArrayList<ParticipantName> targetFollowerList;
    private ArrayList<String> locationList;
    private Participant currentUser;
    private ParticipantName followingName;
    private Participant following;
    private Photograph photo;
    Context mContext;
    private int currentPosition;

    /**
     * Constructor for adapter
     * @param context
     * @param friendList
     */
    public FollowingAdapter(Context context, ArrayList<ParticipantName> friendList,Participant currentUser) {
        super(context, R.layout.fragment_friend, friendList);
        this.followingList = friendList;
        this.mContext = context;
        this.currentUser = currentUser;
    }

    /**
     * getCount method
     * @return size of followingList
     */
    @Override
    public int getCount() {
        return followingList.size();
    }

    /**
     * getItem method
     * @param pos
     * @return the participant at selected location
     */
    @Override
    public ParticipantName getItem(int pos) {
        return followingList.get(pos);
    }

    /**
     * getView method, used to generate the view for FollowingListFragment page
     * also define the onClick method for unfollow button.
     * @param position
     * @param convertView
     * @param parent
     * @return customView, view for FollowingListFragment
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.followed_item,parent,false);
        followingName = getItem(position);
        currentPosition = position;
        String query = "{\n" +
                "	\"query\": {\n" +
                "		\"term\": {\"_id\":\"" + followingName.getId() + "\"}\n" +
                "	}\n" +
                "}";
        ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
        getParticipantsTask.execute(query);

        try {
            if (getParticipantsTask.get() == null) { // check if connected to server
                Toast.makeText(customView.getContext(), "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
            }else if (getParticipantsTask.get().isEmpty() == false) {
                following = getParticipantsTask.get().get(0);
                targetFollowerList = following.getFollowerList();
                followingList = currentUser.getFollowingList();
                photo = following.getAvatar();
                locationList = following.getLocation();
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the participant from the asyc object");
        }


        TextView userName = (TextView) customView.findViewById(R.id.userName);
        ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        TextView location = (TextView) customView.findViewById(R.id.location);
        Button unfollow = (Button) customView.findViewById(R.id.unfollow);

        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFollowing();
                notifyDataSetChanged();
            }
        });

        userName.setText(followingName.getId());
        //get participant's photo and show it in list
        if (photo!=null){
            Bitmap bitmap = photo.getBitMap();
            bitmap = scaleBitmap(bitmap, 80, 80);
            picture.setImageBitmap(bitmap);
        }

        //get the most recent location of participant and show it in list
        try {
            String cityName;
            Geocoder gcd = new Geocoder(customView.getContext(), Locale.getDefault());
            if (locationList.get(0) != null && locationList.get(1)!=null){
                List<Address> addresses = gcd.getFromLocation(Double.parseDouble(locationList.get(0)),
                        Double.parseDouble(locationList.get(1)), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                    location.setText(cityName);
                }
            }else{
                location.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customView;
    }

    /**
     * Remove the selected following participant from following list
     */
    public void removeFollowing(){
        followingList.remove(currentPosition);
        for (int i=0;i<targetFollowerList.size();i++){
            if (targetFollowerList.get(i).equals(currentUser)){
                targetFollowerList.remove(i);
            }
        }
        update(currentUser,following);
    }

    /**
     * Update currentUser and following people
     * @param currentUser
     * @param following
     */
    public void update(Participant currentUser, Participant following){
        ElasticsearchController.AddParticipantsTask addUsersTask1 = new ElasticsearchController.AddParticipantsTask();
        addUsersTask1.execute(currentUser);
        ElasticsearchController.AddParticipantsTask addUsersTask2 = new ElasticsearchController.AddParticipantsTask();
        addUsersTask2.execute(following);
    }


    /**
     * Rescale the bitmap to a fixed height with remaining same aspect ratio
     * Revised from: https://stackoverflow.com/questions/15440647/scaled-bitmap-maintaining-aspect-ratio
     * @param originalImage
     * @param width
     * @param height
     * @return
     */
    public Bitmap scaleBitmap(Bitmap originalImage, int width, int height) {
        Bitmap background = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);

        float originalWidth = originalImage.getWidth();
        float originalHeight = originalImage.getHeight();

        Canvas canvas = new Canvas(background);

        float scale = height / originalHeight;

        float xTranslation = (width - originalWidth * scale) / 2.0f;
        float yTranslation = 0.0f;

        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        canvas.drawBitmap(originalImage, transformation, paint);

        return background;
    }
}