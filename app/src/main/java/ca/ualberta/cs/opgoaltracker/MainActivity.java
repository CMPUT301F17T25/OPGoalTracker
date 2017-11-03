package ca.ualberta.cs.opgoaltracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        configureSignUpButton();
        configureSignInButton();
    }

    @Override
    public void onBackPressed() {
    }



    private void configureSignUpButton(){
        Button addSignUpButton = (Button) findViewById(R.id.signup);
        addSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, Register_activity.class));//Let signup buttion jump to Register activity page.
                finish();
            }

        });
    }


    private void configureSignInButton(){
        Button addSignInButton = (Button) findViewById(R.id.signin);
        addSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, HabitActivity.class));//Let signup buttion jump to Register activity page.
            }

        });
    }
}


