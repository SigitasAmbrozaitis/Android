package com.example.sigit.test2;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

//TOD implement game frames(30fps would be perfect) TOD test
//TOD impelement blocks TODO optional: different colors for different ammount of hits needed
//TOD implement ball(optional:skins)
//TOD implement player(optioanl:skins)
//TOD implement player control(slide with finger/move with buttons < > or both) TODO optional: move with buttons
//TOD implement ball collision with other objects
//TOD implement ball colission with walls
//TOD implement ball speed accelaration(depends on time or collision count)
//TOD implement ball bounce angle calculation from walls mirrored
//TOD implement ball bounce angle calculation from block mirrored
//TOD implement ball bounce angle calculation from player(ball vector + player vector and invert or every different point from player center has increasedingly bigger angle)
//TOD implement HUD showing score
//TODO implement HUD shoving current level
//TOD fix painting error of HUD rectangle(color always black)
//TOD fix bounce from player`s right side
//TOD
// fix errors with game not being able to end when won
//TOD fix collision gliches(not being able to keep up with ball speed)
//TOD implement restart screen
//TOD implement win screen
//TOD implement level change
//TODO improve screen switch speed

//TOD implement better graphics(after gameplay works as intended)
//TOD Start window
//TOD Loose window
//TOD Continue Window
//TOD HUD
//TOD Player
//TOD Ball
//TOD Bricks
//TOD Background


//TODO optional non-deatryoable blocks for harder levels
//TODO multiple levels(10 or more)
//TODO implement level reading from file(for easier level creaation)
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
