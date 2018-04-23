package com.example.sigit.test2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LooseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_loose);

        final MediaPlayer menuMusic = MediaPlayer.create(this, R.raw.menumusic);
        menuMusic.setLooping(true);
        menuMusic.start();

        final Button RetryButton = findViewById(R.id.RetryButton);
        RetryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                menuMusic.stop();
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

                menuMusic.stop();
                //close windows
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

    }
}
