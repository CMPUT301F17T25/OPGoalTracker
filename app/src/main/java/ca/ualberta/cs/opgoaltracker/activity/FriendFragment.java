/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.Participant;
import ca.ualberta.cs.opgoaltracker.models.ParticipantName;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HabitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HabitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendFragment extends Fragment {
    // variables for Friend ListView

    private ArrayList<ParticipantName>  followingList;
    private ArrayList<ParticipantName>  followerList;
    private ArrayList<ParticipantName>  requestList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentActivity context;
    private Participant currentUser;

    private View view;
    private ConstraintLayout constraintLayout;
    private TabLayout tabLayout;

    private OnFragmentInteractionListener mListener;

    public FriendFragment() {
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
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        ((MenuPage) getActivity())
                .setActionBarTitle("My Friends");
        Bundle arg = getArguments();
        currentUser = arg.getParcelable("CURRENTUSER");

        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
        tabLayout = (TabLayout) view.findViewById(R.id.simpleTabLayout);

        // Create a new Tab named "following"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("following"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.ic_launcher); // set an icon for the
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        // Create a new Tab named "follower"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("follower"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.ic_launcher); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

        // Create a new Tab named "request"
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("request"); // set the Text for the second Tab
        thirdTab.setIcon(R.drawable.ic_launcher); // set an icon for the second tab
        tabLayout.addTab(thirdTab); // add  the tab  in the TabLayout

        String ID = currentUser.getId();
        String query = "{\n" +
                "	\"query\": {\n" +
                "		\"term\": {\"_id\":\"" + ID + "\"}\n" +
                "	}\n" +
                "}";
        ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
        getParticipantsTask.execute(query);

        try {
            if (getParticipantsTask.get() == null) { // check if connected to server
                Toast.makeText(getActivity(), "Can Not Connect to Server", Toast.LENGTH_SHORT).show();
            }else if(getParticipantsTask.get().isEmpty() == false){
                currentUser = getParticipantsTask.get().get(0);
                followingList=currentUser.getFollowingList();
                followerList=currentUser.getFollowerList();
                requestList=currentUser.getRequestList();
            }else{
                Toast.makeText(getActivity(), "No user can be found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the participant from the asyc object");
        }

        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("followingList", followingList);
        bundle.putParcelableArrayList("followerList", followerList);
        bundle.putParcelableArrayList("requestList", requestList);
        bundle.putParcelable("LOGINUSER",currentUser);

        //go to FriendSearchActivty
        FloatingActionButton addFriend = (FloatingActionButton) view.findViewById(R.id.add_friend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), FriendSearchActivity.class);
                intent.putParcelableArrayListExtra("followingList", followingList);
                intent.putExtra("LOGINUSER", currentUser);
                startActivity(intent);
            }
        });
        Fragment fragment = null;
        fragment = new FollowingListFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.constraintLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FollowingListFragment();
                        fragment.setArguments(bundle);
                        break;
                    case 1:
                        fragment = new FollowerListFragment();
                        fragment.setArguments(bundle);
                        break;
                    case 2:
                        fragment = new RequestListFragment();
                        fragment.setArguments(bundle);
                        break;
                }
                FragmentManager fm = context.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.constraintLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        context = (FragmentActivity) activity;
        super.onAttach(activity);
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
     * >Communicating with Other Fragments</a> for more informatio 256n.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
