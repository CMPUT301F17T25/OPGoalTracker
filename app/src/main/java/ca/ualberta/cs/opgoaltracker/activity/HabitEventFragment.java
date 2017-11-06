package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.opgoaltracker.R;
import ca.ualberta.cs.opgoaltracker.exception.CommentTooLongException;
import ca.ualberta.cs.opgoaltracker.models.Habit;
import ca.ualberta.cs.opgoaltracker.models.HabitEvent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HabitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HabitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

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

        ArrayList<HabitEvent> displayList = new ArrayList<HabitEvent>();
        try {
            displayList.add(new HabitEvent("test","this is comment",new Date()));
        } catch (CommentTooLongException e) {
            e.printStackTrace();
        }
        try {
            displayList.add(new HabitEvent("test","test picture",new Date()));
        } catch (CommentTooLongException e) {
            e.printStackTrace();
        }
        HabitEventAdapter adapter = new HabitEventAdapter(getActivity(),displayList);
        final ListView listview=(ListView)view.findViewById(R.id.list_event);
        listview.setAdapter(adapter);

        //handles listview clicks
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selected = listview.getItemAtPosition(i);
                HabitEvent selectedEvent = (HabitEvent) selected;


                Intent intent = new Intent(getActivity(), EventInfoActivity.class);
                intent.putExtra("type",selectedEvent.getHabitType());
                intent.putExtra("comment",selectedEvent.getComment());
                intent.putExtra("location",(selectedEvent.getLocation()==null));
                if (selectedEvent.getComment()=="test picture") {
                    //put extra picture
                    Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.testpic);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pic.compress(Bitmap.CompressFormat.PNG,50,stream);
                    intent.putExtra("picture",stream.toByteArray());
                    // picture putExtra taken from
                    //https://stackoverflow.com/questions/4352172/how-do-you-pass-images-bitmaps-between-android-activities-using-bundles/7890405#7890405
                    //17-11-06
                }
                startActivity(intent);
            }
        });
        //handles button
        FloatingActionButton add_event = (FloatingActionButton) view.findViewById(R.id.new_event);
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HabitEventAddActivity.class);
                startActivity(intent);
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
