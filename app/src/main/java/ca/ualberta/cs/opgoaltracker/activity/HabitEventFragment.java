/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.opgoaltracker.Controller.ElasticsearchController;
import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.UndefinedException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;
import ca.ualberta.cs.opgoaltracker.models.HabitList;
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
    private View view;

    private HabitEvent beforeDetail=null;



    private ArrayList<HabitEvent> displayList;
    private ArrayList<HabitEvent> fullList;
    private ArrayList<String> habitList;

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
        fullList = new ArrayList<HabitEvent>();
        fillDisplayList();
        //fill displayList
        Collections.sort(displayList, new Comparator<HabitEvent>() {
            @Override
            public int compare(HabitEvent habitEvent, HabitEvent t1) {
                return -habitEvent.getDate().compareTo(t1.getDate());
            }
        });
        habitList=new ArrayList<String>();
        for (Habit h:currentUser.getHabitList().getArrayList()){
            habitList.add(h.getHabitType());
        }
        // add events to test the adapter
        // probly name another variable arraylist type to store all events
        //display list's stuff gets removed during search

        HabitEventAdapter adapter = new HabitEventAdapter(getActivity(),displayList);
        final ListView listview=(ListView)view.findViewById(R.id.list_event);
        listview.setAdapter(adapter);

        //handles listview clicks
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selected = listview.getItemAtPosition(i);
                HabitEvent selectedEvent = (HabitEvent) selected;
                beforeDetail=selectedEvent;
                Intent intent = new Intent(getActivity(), EventInfoActivity.class);
                intent.putExtra("event",selectedEvent);
                startActivityForResult(intent,31);
            }
        });
        //handles button
        FloatingActionButton add_event = (FloatingActionButton) view.findViewById(R.id.new_event);
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (habitList.isEmpty()){
                    Toast.makeText(getActivity(),"Plase have atleast 1 habit type",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getActivity(), HabitEventAddActivity.class);
                    intent.putStringArrayListExtra("hlist", habitList);
                    startActivityForResult(intent, 30);
                }
            }
        });

        //ActionBar actionBar = getActivity().getActionBar();





        return view;

    }

    private void fillDisplayList() {
        for (Habit h:currentUser.getHabitList().getArrayList()) {
            for (HabitEvent e : h.getEventList()) {
                displayList.add(e);
                fullList.add(e);
            }
        }
        Collections.sort(displayList, new Comparator<HabitEvent>() {
            @Override
            public int compare(HabitEvent habitEvent, HabitEvent t1) {
                return -habitEvent.getDate().compareTo(t1.getDate());
            }
        });
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

                ArrayList<String> searchList = new ArrayList<String>();
                searchList.add("");
                searchList.addAll(habitList);
                final Spinner searchHabit = (Spinner)dialog.findViewById(R.id.search_spinner);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, searchList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchHabit.setAdapter(dataAdapter);

                final EditText message = (EditText) dialog.findViewById(R.id.search_event_text);
                message.setText("");
                final Button search = (Button) dialog.findViewById(R.id.search_search);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //// TODO: 2017-12-01 add search 
                    public void onClick(View view) {

                        if (!searchHabit.getSelectedItem().equals("") && !message.getText().toString().equals("")){
                            Log.d("window","first");
                            displayList=new ArrayList<HabitEvent>();
                            for (HabitEvent e:fullList){
                                if (e.getHabitType().equals(searchHabit.getSelectedItem()) &&
                                        e.getComment().contains(message.toString())){
                                    displayList.add(e);
                                }
                            }
                        }else if (!searchHabit.getSelectedItem().equals("")){
                            Log.d("window","2");
                            displayList=new ArrayList<HabitEvent>();
                            for (HabitEvent e:fullList) {
                                if (e.getHabitType().equals(searchHabit.getSelectedItem())) {
                                    displayList.add(e);
                                }
                            }
                        }else if (!message.getText().toString().equals("")){
                            Log.d("window","3");
                            Log.d("window",message.getText().toString());
                            displayList=new ArrayList<HabitEvent>();
                            for (HabitEvent e:fullList) {
                                if (e.getComment().contains(message.getText().toString())) {
                                    displayList.add(e);
                                }
                            }
                        }else{
                            Log.d("window","4");
                            displayList=fullList;
                        }
                        Collections.sort(displayList, new Comparator<HabitEvent>() {
                            @Override
                            public int compare(HabitEvent habitEvent, HabitEvent t1) {
                                return -habitEvent.getDate().compareTo(t1.getDate());
                            }
                        });
                        HabitEventAdapter adapter = new HabitEventAdapter(getActivity(),displayList);
                        ListView listview=(ListView)getView().findViewById(R.id.list_event);
                        listview.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                dialog.show();
                return true;
            case R.id.event_menu_map:
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                intent.putExtra("DISPLAYLIST",displayList);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 30) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                HabitEvent a = data.getParcelableExtra("event");
                // add new event to the display list
                displayList.add(a);
                fullList.add(a);

                //sort the display list
                Collections.sort(displayList, new Comparator<HabitEvent>() {
                    @Override
                    public int compare(HabitEvent habitEvent, HabitEvent t1) {
                        return -habitEvent.getDate().compareTo(t1.getDate());
                    }
                });

                //display the updated event list
                HabitEventAdapter adapter = new HabitEventAdapter(getActivity(), displayList);
                final ListView listview = (ListView) view.findViewById(R.id.list_event);
                listview.setAdapter(adapter);


                HabitList userHabits = currentUser.getHabitList();
                ArrayList<Habit> habitTypes = userHabits.getArrayList();
                for (Habit habit:habitTypes){

                    if (habit.getHabitType().equals(a.getHabitType())){
                        Log.d("new event","added to event");
                        habit.newEvent(a);
                        //updates the habit on the server
                        ElasticsearchController.AddHabitsTask addHabitsTask = new ElasticsearchController.AddHabitsTask();
                        addHabitsTask.execute(habit);

                        break;
                    }
                }
                userHabits.setArrayList(habitTypes);
                try {
                    currentUser.setHabitList(userHabits);
                } catch (UndefinedException e) {
                    e.printStackTrace();
                }
                //// TODO: 2017-12-01  
                //UPLOAD TO ELASTIC SEARCH
                //update both the habit and the user

                if (a.getLocation().get(0)!=null) {
                    currentUser.setLocation(a.getLocation().get(0), a.getLocation().get(1));
                }
                ElasticsearchController.AddParticipantsTask addUsersTask = new ElasticsearchController.AddParticipantsTask();
                addUsersTask.execute(currentUser);
                Log.d("new event","upload to server");
            }
        } else if(requestCode == 31) {
            // if the activity was to edit/see info of event
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                HabitEvent a = data.getParcelableExtra("event");
                Boolean changedPhoto = data.getBooleanExtra("photo", Boolean.FALSE);
                int delete = data.getIntExtra("delete",0);
                if (delete==1){
                    displayList.remove(beforeDetail);
                    fullList.remove(beforeDetail);
                    Collections.sort(displayList, new Comparator<HabitEvent>() {
                        @Override
                        public int compare(HabitEvent habitEvent, HabitEvent t1) {
                            return -habitEvent.getDate().compareTo(t1.getDate());
                        }
                    });
                    HabitEventAdapter adapter = new HabitEventAdapter(getActivity(), displayList);
                    ListView listview = (ListView) view.findViewById(R.id.list_event);
                    listview.setAdapter(adapter);

                    HabitList userHabits = currentUser.getHabitList();
                    ArrayList<Habit> habitTypes = userHabits.getArrayList();
                    for (Habit habit : habitTypes) {
                        if (habit.getHabitType().equals(beforeDetail.getHabitType())) {
                            Log.d("change","changed");
                            habit.removeEvent(beforeDetail);
                            //update habit
                            ElasticsearchController.AddHabitsTask addHabitsTask = new ElasticsearchController.AddHabitsTask();
                            addHabitsTask.execute(habit);
                        }
                    }
                    try {
                        currentUser.setHabitList(userHabits);
                    } catch (UndefinedException e) {
                        e.printStackTrace();
                    }
                    //// TODO: 2017-12-01
                    //UPLOAD TO ELASTIC SEARCH
                    //update both the habit and the user
                    Log.d("delete event","upload to server");
                    ElasticsearchController.AddParticipantsTask addUsersTask = new ElasticsearchController.AddParticipantsTask();
                    addUsersTask.execute(currentUser);



                } else if (beforeDetail.changed(a) || changedPhoto == Boolean.TRUE) {
                    //// TODO: 2017-12-01 delete this
                    //handles changed event
                    displayList.remove(beforeDetail);
                    fullList.remove(beforeDetail);
                    displayList.add(a);
                    fullList.add(a);
                    fullList.remove(beforeDetail);
                    Collections.sort(displayList, new Comparator<HabitEvent>() {
                        @Override
                        public int compare(HabitEvent habitEvent, HabitEvent t1) {
                            return -habitEvent.getDate().compareTo(t1.getDate());
                        }
                    });
                    HabitEventAdapter adapter = new HabitEventAdapter(getActivity(), displayList);
                    ListView listview = (ListView) view.findViewById(R.id.list_event);
                    listview.setAdapter(adapter);

                    HabitList userHabits = currentUser.getHabitList();
                    ArrayList<Habit> habitTypes = userHabits.getArrayList();
                    for (Habit habit : habitTypes) {
                        if (habit.getHabitType().equals(a.getHabitType())) {
                            Log.d("change","changed");
                            habit.removeEvent(beforeDetail);
                            habit.newEvent(a);
                            //update habit
                            ElasticsearchController.AddHabitsTask addHabitsTask = new ElasticsearchController.AddHabitsTask();
                            addHabitsTask.execute(habit);
                        }
                    }
                    try {
                        currentUser.setHabitList(userHabits);
                    } catch (UndefinedException e) {
                        e.printStackTrace();
                    }
                    //// TODO: 2017-12-01
                    //UPLOAD TO ELASTIC SEARCH
                    //update both the habit and the user
                    Log.d("new event","upload to server");
                    if (a.getLocation().get(0)!=null) {
                        currentUser.setLocation(a.getLocation().get(0), a.getLocation().get(1));
                    }
                    ElasticsearchController.AddParticipantsTask addUsersTask = new ElasticsearchController.AddParticipantsTask();
                    addUsersTask.execute(currentUser);
                }

            }
        }


    }
}
