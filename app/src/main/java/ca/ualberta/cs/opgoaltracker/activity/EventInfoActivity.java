package ca.ualberta.cs.opgoaltracker.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
        Button saveChanges = (Button) findViewById(R.id.create_event);
        saveChanges.setText("Save Changes");

        comment.setText(getIntent().getStringExtra("comment"));
        if (getIntent().getBooleanExtra("location",false)){
            location.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.event_event_delete:
                return true;
            // delete event
        }
        return super.onOptionsItemSelected(item);
    }
}
