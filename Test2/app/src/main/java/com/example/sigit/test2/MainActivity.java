package com.example.sigit.test2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sigit.test2.GameActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer menuMusic = MediaPlayer.create(this, R.raw.menumusic);
        menuMusic.setLooping(true);
        menuMusic.start();

        //exit button setup
        final Button ExitButton = findViewById(R.id.ExitButton);
        ExitButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                menuMusic.stop();
                //close all windows
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        });

        //start button setup
        final Button StartButton = findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                menuMusic.stop();
                //open new window
                Intent GameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(GameIntent);


            }
        });
    }


}
