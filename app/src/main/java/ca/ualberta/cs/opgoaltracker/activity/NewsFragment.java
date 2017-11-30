/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
    private ArrayList<HabitEvent> toMap;

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
        //((MenuPage) getActivity()).setActionBar();
        Bundle arg = getArguments();
        currentUser = arg.getParcelable("CURRENTUSER");
        ArrayList<ParticipantName> followingList;

        try {
            followingList = currentUser.getFollowingList();
        } catch (UndefinedException e) {
            e.printStackTrace();
            followingList = new ArrayList<ParticipantName>();
        }
        ArrayList<NewsUserEventPair> pairList = getPairs(followingList);
        NewsAdapter adapter = new NewsAdapter(getContext(),pairList);
        ListView listview=(ListView)view.findViewById(R.id.newsList);
        listview.setAdapter(adapter);

        return view;

    }

    private ArrayList<NewsUserEventPair> getPairs(ArrayList<ParticipantName> followingList) {
        ArrayList<NewsUserEventPair> news = new ArrayList<NewsUserEventPair>();
        //test
        for (ParticipantName participant : followingList){
            String id = participant.getId();
            //UNSURE ABOUt THIS QUERY
            String query = "{\n" +
                    "	\"query\": {\n" +
                    "		\"term\": {\"id\":\"" + id + "\"}\n" +
                    "	}\n" +
                    "}";
            ElasticsearchController.GetHabitsTask getHabitsTask = new ElasticsearchController.GetHabitsTask();
            getHabitsTask.execute(query);
            try {
                ArrayList<Habit> habits = getHabitsTask.get();
                for (Habit habit: habits){
                    String query2="{\n" +
                            "	\"query\": {\n" +
                            "		\"term\": {\"habitType\":\"" + id + "\"}\n" +
                            "	},\n" +
                            "   \"sort\": { \"date\":{\"order\":\"desc\"}}"+
                            "}";
                    ElasticsearchController.GetHabitEventsTask getHabitEventsTask =
                            new ElasticsearchController.GetHabitEventsTask();
                    getHabitEventsTask.execute(query2);
                    ArrayList<HabitEvent>sortedEvents = getHabitEventsTask.get();
                    if (sortedEvents.isEmpty()==Boolean.FALSE) {
                        toMap.add(sortedEvents.get(0));
                        news.add(new NewsUserEventPair(participant, sortedEvents.get(0)));
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
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
}
