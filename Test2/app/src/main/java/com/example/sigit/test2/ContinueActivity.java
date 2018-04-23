package com.example.sigit.test2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContinueActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);


        final Button ContinueButton = findViewById(R.id.ContinueButton);
        ContinueButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //start new window
                Intent GameIntent = new Intent(ContinueActivity.this, GameActivity.class);
                startActivity(GameIntent);

                //close old window
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        final Button ExitButton = findViewById(R.id.ExitButton);
        ExitButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //close all procesess
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

    }
}
