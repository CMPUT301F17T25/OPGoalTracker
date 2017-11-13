/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;

/**
 * FollowerListFragment is called when user tap the 'follower' tab in FriendFragment
 * It will generate a fragment of user's follower's list
 *
 * @author song
 * @version 1.0
 * @since FollowerList
 * @see Fragment
 *
 */
public class FollowerListFragment extends Fragment {

    private ArrayList<Participant>  followerList;
    private ListView listView;
    private FollowerAdapter adapter;

    /**
     * Required empty public constructor
     */
    public FollowerListFragment(){}

    /**
     * default onCreate method
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
     * Through this onCreateView method, a fragment is generated using fragment_friend layout and FollowerAdapter
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view of fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_friend, container, false);
        Bundle arg = getArguments();
        followerList = arg.getParcelableArrayList("followerList");

        listView = (ListView) view.findViewById(R.id.friendlist);
        adapter = new FollowerAdapter(getActivity(),followerList);
        listView.setAdapter(adapter);
        Button unfollow = (Button) view.findViewById(R.id.unfollow);

        //test
        if (followerList.isEmpty()){
            followerList.add(new Participant("sdc"));
        }

        return view;
    }

}