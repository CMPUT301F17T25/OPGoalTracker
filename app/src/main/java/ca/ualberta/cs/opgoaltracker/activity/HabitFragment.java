package ca.ualberta.cs.opgoaltracker.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.ualberta.cs.opgoaltracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HabitFragment extends Fragment {


    public HabitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit, container, false);


    }

}
