package com.example.sigit.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_loose);

        final Button RetryButton = findViewById(R.id.RetryButton);
        RetryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //open new window
                Intent GameIntent = new Intent(LooseActivity.this, GameActivity.class);
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
                //close windows
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

    }
}
