/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.Participant;

/**
 * This is the fragment that displays a user's own habit event
 * <br>
 * This page allows user to see own events in a listview and has the option of creating new events
 * <br>
 * @author Long Ma
 * @version 2.0
 * @see AppCompatActivity
 * @since 2.0
 */
public class HabitEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MenuItem searchButton;
    private MenuItem mapButton;
    private Participant currentUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;


    ArrayList<HabitEvent> displayList;

    private OnFragmentInteractionListener mListener;

    public HabitEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitEventFragment newInstance(String param1, String param2) {
        HabitEventFragment fragment = new HabitEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_habit_event, container, false);
        ((MenuPage) getActivity())
                .setActionBarTitle("Habit Event");
        Bundle arg = getArguments();
        currentUser = arg.getParcelable("CURRENTUSER");

        displayList = new ArrayList<HabitEvent>();
        // add events to test the adapter

        HabitEventAdapter adapter = new HabitEventAdapter(getActivity(),displayList);
        final ListView listview=(ListView)view.findViewById(R.id.list_event);
        listview.setAdapter(adapter);

        //handles listview clicks
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selected = listview.getItemAtPosition(i);
                HabitEvent selectedEvent = (HabitEvent) selected;


                Intent intent = new Intent(getActivity(), EventInfoActivity.class);
                intent.putExtra("type",selectedEvent.getHabitType());
                intent.putExtra("comment",selectedEvent.getComment());
                intent.putExtra("location",(selectedEvent.getLocation()==null));
                if (selectedEvent.getComment()=="test picture") {
                    //put extra picture
                    Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.testpic);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pic.compress(Bitmap.CompressFormat.PNG,50,stream);
                    intent.putExtra("picture",stream.toByteArray());
                    // picture putExtra taken from
                    //https://stackoverflow.com/questions/4352172/how-do-you-pass-images-bitmaps-between-android-activities-using-bundles/7890405#7890405
                    //17-11-06
                }
                startActivityForResult(intent,0);
            }
        });
        //handles button
        FloatingActionButton add_event = (FloatingActionButton) view.findViewById(R.id.new_event);
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HabitEventAddActivity.class);
                startActivityForResult(intent,1);
            }
        });

        //ActionBar actionBar = getActivity().getActionBar();





        return view;

    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_habit_event, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.event_menu_search :
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_search);
                dialog.setTitle("Search Events");
                EditText message = (EditText) dialog.findViewById(R.id.search_event_text);
                Button search = (Button) dialog.findViewById(R.id.search_search);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){ //if the activity was to create a new event
            case 1:
                if (resultCode== AppCompatActivity.RESULT_OK) {
                    Log.d("start", "end");
                    HabitEvent a = data.getParcelableExtra("event");
                    displayList.add(a);
                    HabitEventAdapter adapter = new HabitEventAdapter(getActivity(), displayList);
                    final ListView listview = (ListView) view.findViewById(R.id.list_event);
                    listview.setAdapter(adapter);

                    Log.d("return", "event");
                }
                break;
        }
    }
}
