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
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.NoTitleException;
import ca.ualberta.cs.opgoaltracker.exception.StringTooLongException;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitList;
import ca.ualberta.cs.opgoaltracker.models.Participant;

import static android.R.id.summary;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HabitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HabitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitFragment extends Fragment {

    // variables for Habit ListView
    private Participant currentUser;
    private HabitList habitList;
    private ListView lvHabit;
    private HabitAdapter adapter;
    private static final int REQUEST_CODE_ONE = 1;
    private static final int REQUEST_CODE_TWO = 2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

    private OnFragmentInteractionListener mListener;

    public HabitFragment() {
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
    public static HabitFragment newInstance(String param1, String param2) {
        HabitFragment fragment = new HabitFragment();
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

    /**
     * Create a fragment with data passed in as Bundle
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_habit, container, false);
        ((MenuPage) getActivity())
                .setActionBarTitle("Habit");
        Bundle arg = getArguments();
        currentUser = arg.getParcelable("CURRENTUSER");

        // create Habit ListView
        // TODO: change this part to fit the variable passed from MainActivity
        lvHabit = (ListView) view.findViewById(R.id.list_habit);
        if (currentUser == null) {
            currentUser = new Participant("111");

        }
        habitList = currentUser.getHabitList();

        // Init adapter
        habitList.sort();
        adapter = new HabitAdapter(getActivity(), habitList.getArrayList());
        lvHabit.setAdapter(adapter);

        // jump to HabitAddActivity if floating action button is clicked
        FloatingActionButton add_habit = (FloatingActionButton) view.findViewById(R.id.add_habit);
        add_habit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), HabitAddActivity.class);
                intent.putExtra("user",currentUser.getId());
                startActivityForResult(intent,REQUEST_CODE_ONE);
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

//        // refresh ListView
//        adapter = new HabitAdapter(getActivity(), habitList);
//        lvHabit.setAdapter(adapter);
//        adapter.notifyDataSetChanged();


        // call HabitDetailActivity by click rows of ListView
        lvHabit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HabitDetailActivity.class);
//                intent.putExtra("Habit", (Parcelable) habitList.get(position));
                intent.putParcelableArrayListExtra("HabitList", habitList.getArrayList());
                intent.putExtra("position", position);
                intent.putExtra("user",currentUser.getId());
                startActivityForResult(intent, REQUEST_CODE_TWO);
            }
        });
    }

    /**
     * Receive data from HabitAddActivity and HabitDetailActivity, then refresh HabitFragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void  onActivityResult(int requestCode,int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ONE) { // update ListView after adding new Habit
            if (resultCode == RESULT_OK) {
                Habit newHabit = data.getParcelableExtra("Habit");
                habitList.addHabit(newHabit);
            }
        } else if (requestCode == REQUEST_CODE_TWO) { // update ListView after editing Habit
            if (resultCode == RESULT_OK) {
                ArrayList<Habit> newHabitList = data.getExtras().getParcelableArrayList("HabitList");
                habitList.setArrayList(newHabitList);
            }
        }

        // sort habitList and refresh
        habitList.sort();
//        adapter.notifyDataSetChanged();
        adapter = new HabitAdapter(getActivity(), habitList.getArrayList());
        lvHabit.setAdapter(adapter);

        // upload currentUser
        ElasticsearchController.AddParticipantsTask addUsersTask = new ElasticsearchController.AddParticipantsTask();
        addUsersTask.execute(currentUser);
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
}
