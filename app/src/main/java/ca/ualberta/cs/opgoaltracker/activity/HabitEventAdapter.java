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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;

/**
 * Created by malon_000 on 2017-11-03.
 */


/**
 * This is an adapter to put habitevents onto listview
 * Used in HabitEventFragment
 *
 * @author Long Ma
 * @version 3.0
 * @see ArrayAdapter
 * @see HabitEventFragment
 * @since 3.0
 */
class HabitEventAdapter extends ArrayAdapter<HabitEvent> {
    private static final int SCALED_IMAGE_WIDTH = 500;

    public HabitEventAdapter(Context context, List<HabitEvent> objects) {
        super(context, R.layout.fragment_habit_event, objects);
    }

    /**
     * sets the view of the adapter
     * @param position: position of the view
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //use elastic search to get user's info but for since this is just a test
        LayoutInflater EventInflater = LayoutInflater.from(getContext());
        View customView = EventInflater.inflate(R.layout.event_row,parent,false);

        HabitEvent event = getItem(position);
        TextView EventType = (TextView) customView.findViewById(R.id.eventType);
        TextView EventComment = (TextView) customView.findViewById(R.id.eventComment);
        TextView EventDate = (TextView) customView.findViewById(R.id.eventDate);
        ImageView EventImage = (ImageView) customView.findViewById(R.id.eventImage);


        EventType.setText(event.getHabitType());
        EventComment.setText(event.getComment());
        EventDate.setText(event.getDate().toString());
        if (event.getPhoto()!=null) {
            Bitmap bitmap = event.getPhoto().getBitMap();
            bitmap = scaleBitmap(bitmap, 1000, 500);

            EventImage.setImageBitmap(bitmap);
        }else{
            EventImage.setImageDrawable(null);
        }
        return customView;
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
