/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.NewsUserEventPair;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    private Participant currentUser;
    ArrayList<NewsUserEventPair> pairList;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        ((MenuPage) getActivity())
                .setActionBarTitle("News");

        Bundle arg = getArguments();
        currentUser = arg.getParcelable("CURRENTUSER");
        ArrayList<ParticipantName> followingList;
        Log.d("hello","yes");

        try {
            followingList = currentUser.getFollowingList();

        } catch (UndefinedException e) {
            e.printStackTrace();
            followingList = new ArrayList<ParticipantName>();
        }
        pairList = getPairs(followingList);
        NewsAdapter adapter = new NewsAdapter(getContext(),pairList);
        ListView listview=(ListView)view.findViewById(R.id.newsList);
        listview.setAdapter(adapter);

        return view;

    }

    private ArrayList<NewsUserEventPair> getPairs(ArrayList<ParticipantName> followingList) {
        ArrayList<NewsUserEventPair> news = new ArrayList<NewsUserEventPair>();
        Collections.sort(followingList, new Comparator<ParticipantName>() {
            @Override
            public int compare(ParticipantName participantName, ParticipantName t1) {
                return participantName.getId().compareTo(t1.getId());
            }
        });
        //test
        for (ParticipantName participant : followingList){
            Log.d("search","now:"+participant.getId());
            String id = participant.getId();
            String query = "{\n" +
                    "	\"query\": {\n" +
                    "		\"term\": {\"_id\":\"" + participant.getId() + "\"}\n" +
                    "	}\n" +
                    "}";
            ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
            getParticipantsTask.execute(query);
            try {
                ArrayList<Participant> followeeList = getParticipantsTask.get();
                if (getParticipantsTask.get() == null) { // check if connected to server
                    Toast.makeText(getContext(), "Can not Connect to Server", Toast.LENGTH_SHORT).show();
                }else if (followeeList.isEmpty() == false){
                    for (Participant followee: followeeList){
                        for (Habit followeeHabit :followee.getHabitList().getArrayList()){
                            news.add(new NewsUserEventPair(followee.getId(),followeeHabit.getLatest(),followee.getAvatar()));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




            /**

            //UNSURE ABOUt THIS QUERY
            String query =
                    "{\n" +
                    "   \"from\" : 0, \"size\" : 100," +
                    "	\"query\": {\n" +
                    "		\"term\": {\"owner\" : \"" + id + "\"}\n" +
                    "	}\n" +
                    "}"; // "size": 100 is necessary to get more than 10 result
            ElasticsearchController.GetHabitsTask getHabitsTask = new ElasticsearchController.GetHabitsTask();
            getHabitsTask.execute(query);
            ArrayList<Habit> habits = null;
            try {
                Log.d("event","try");
                if (getHabitsTask.get() == null) { // check if connected to server
                    Toast.makeText(getActivity(), "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
                } else {
                    habits =getHabitsTask.get();
                    Collections.sort(habits, new Comparator<Habit>() {
                        @Override
                        public int compare(Habit habit, Habit t1) {
                            return habit.getHabitType().compareTo(t1.getHabitType());
                        }
                    });

                    for (Habit habit:habits){
                        if (habit.getLatest()!=null) {
                            news.add(new NewsUserEventPair(participant, habit.getLatest()));
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            */
        }
        if (news.size()==0){
            Log.d("event","none");
            Toast.makeText(getContext(),"Your news page is empty, either we couldn't contact the server or you have no followees, start following people in the friends tab",
                    Toast.LENGTH_LONG).show();
        }else{Log.d("event","once");}
        return news;
    }


    ;

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
        inflater.inflate(R.menu.menu_news, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.news_map) {
            Intent intent = new Intent(getActivity(),MapsActivity.class);
            ArrayList<HabitEvent> display = new ArrayList<HabitEvent>();
            for (int i = 0; i < pairList.size(); i++) {
                    display.add(pairList.get(i).getEvent());
                }
            intent.putExtra("DISPLAYLIST",display);
            startActivity(intent);
        }
        return Boolean.FALSE;
    }
}
