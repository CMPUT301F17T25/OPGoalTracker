package ca.ualberta.cs.opgoaltracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Register_activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_activity);
        configureTrySignInButton();
        configureRegisterButton();
    }

    private void configureTrySignInButton(){
        Button addTrySignInButton = (Button) findViewById(R.id.trysignin);
        addTrySignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(Register_activity.this, MainActivity.class));//Let signup buttion jump to Register activity page.
                finish();
            }

        });
    }

    private void configureRegisterButton(){
        Button addRegisterButton = (Button) findViewById(R.id.register);
        addRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(Register_activity.this, HabitActivity.class));//Let signup buttion jump to Register activity page.
                finish();
            }

        });
    }
}
