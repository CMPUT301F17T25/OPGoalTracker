package ca.ualberta.cs.opgoaltracker;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by malon_000 on 2017-11-03.
     */

class NewsAdapter extends ArrayAdapter<HabitEvent> {
    public NewsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<HabitEvent> objects) {
        super(context, resource, objects);
    }
}
