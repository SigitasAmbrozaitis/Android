package com.example.sigit.test2;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

//TOD implement game frames(30fps would be perfect) TOD test
//TODO impelement blocks(optional: different colors for different ammount of colors needed)
//TOD implement ball(optional:skins)
//TOD implement player(optioanl:skins)
//TOD implement player control(slide with finger/move with buttons < > or both) TODO optional: move with buttons
//TOD implement ball collision with other objects
//TOD implement ball colission with walls
//TODO implement ball speed accelaration(depends on time or collision count)
//TOD implement ball bounce angle calculation from walls mirrored
//TODO implement ball bounce angle calculation from block mirrored
//TODO implement ball bounce angle calculation from player(ball vector + player vector and invert or every different point from player center has increasedingly bigger angle)

//TODO optional non-deatryoable blocks for harder levels
//TODO multiple levels(10 or more)
//TODO challenge mode, continious spawnning of block(time or collision dependent)
//TODO sound effect(on collision, on loose, on level completion)


public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //allows fullscreen and remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //get window size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point(0,0);
        display.getSize(size);

        //create new view
        setContentView(new GamePanel(this, size));
    }
}
