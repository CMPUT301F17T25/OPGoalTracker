/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;

/**
 * Created by song on 2017/11/24.
 */

public class RequestListFragment extends Fragment {
    private ArrayList<ParticipantName> requestList;
    private ListView listView;
    private RequestAdapter adapter;
    private Participant currentUser;
    /**
     * Required empty public constructor
     */
    public RequestListFragment() {
        // Required empty public constructor
    }

    /**
     * default onCreate method
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * default onStart method
     */
    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * Through this onCreateView method, a fragment is generated using fragment_friend layout and FollowingAdapter
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view of fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_friend, container, false);
        listView = (ListView) view.findViewById(R.id.friendlist);
        Bundle arg = getArguments();
        requestList = arg.getParcelableArrayList("requestList");
        currentUser= arg.getParcelable("LOGINUSER");
        adapter = new RequestAdapter(getActivity(),requestList,currentUser);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}
