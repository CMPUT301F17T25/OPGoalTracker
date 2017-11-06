package ca.ualberta.cs.opgoaltracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.ualberta.cs.opgoaltracker.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        configureSignUpButton();
    }

    private void configureSignUpButton(){
        Button addLogoutButton = (Button) findViewById(R.id.logout);
        addLogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                //Let logout buttion jump to main activity page.
                finish();
            }
        });
    }
}
