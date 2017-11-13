/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.models.Participant;

/**
 * FollowingListFragment is called when user tap the 'following' tab in FriendFragment
 * It will generate a fragment of user's following list
 *
 * @author song
 * @version 1.0
 * @since FollowerList
 * @see Fragment
 */
public class FollowingListFragment extends Fragment {

    private ArrayList<Participant> followingList;
    private ListView listView;
    private FollowingAdapter adapter;

    /**
     * Required empty public constructor
     */
    public FollowingListFragment() {
        // Required empty public constructor
    }

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
        Bundle arg = getArguments();
        followingList = arg.getParcelableArrayList("followingList");
        listView = (ListView) view.findViewById(R.id.friendlist);
        adapter = new FollowingAdapter(getActivity(),followingList);
        listView.setAdapter(adapter);

        return view;
    }

}