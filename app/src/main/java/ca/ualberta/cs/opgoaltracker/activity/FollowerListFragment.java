/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.FriendList;
import ca.ualberta.cs.opgoaltracker.models.Participant;

public class FollowerListFragment extends Fragment {

    private ArrayList<Participant>  followerList;
    private ListView listView;
    private FriendAdapter adapter;


    public FollowerListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_friend, container, false);
        Bundle arg = getArguments();
        followerList = arg.getParcelableArrayList("followerList");

        listView = (ListView) view.findViewById(R.id.friendlist);
        adapter = new FriendAdapter(getActivity(),followerList);
        listView.setAdapter(adapter);

        //test
        if (followerList.isEmpty()){
            followerList.add(new Participant("sdc"));
        }

        return view;
    }

}