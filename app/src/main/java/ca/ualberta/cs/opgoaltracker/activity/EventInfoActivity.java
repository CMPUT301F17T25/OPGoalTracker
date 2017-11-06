package ca.ualberta.cs.opgoaltracker.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import ca.ualberta.cs.opgoaltracker.R;

/**
 * Created by malon_000 on 2017-11-06.
 */

public class EventInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

            //set picture
        ImageButton imageButton = (ImageButton) findViewById(R.id.new_event_picture);
        if (getIntent().hasExtra("picture")) {
            Bitmap picture = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("picture"),0,getIntent().getByteArrayExtra("picture").length);
            imageButton.setImageBitmap(picture);
        }
        EditText comment= (EditText) findViewById(R.id.new_event_comment);
        CheckBox location = (CheckBox) findViewById(R.id.new_event_location);

        comment.setText(getIntent().getStringExtra("comment"));
        if (getIntent().getBooleanExtra("location",false)){
            location.setChecked(true);
        }



    }
}
