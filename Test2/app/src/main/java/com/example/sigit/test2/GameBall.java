package com.example.sigit.test2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameBall implements GameObject {

    private boolean dead;       //dead if hit bottom floor
    private float x;            //x coordinates of ball center
    private float y;            //y coordinates of ball center
    private float range;        //ball range
    private Vector vector;      //ball vector that determines how ball moves
    private int speed = 20;     //ball speed
    private int maxSpeed=60;    //ball max speed
    private Point winSize;      //device screen size
    private GamePanel panel;    //game panel
    private Bitmap Ball;

    public GameBall(Point location, float range, Point winSize, GamePanel panel)
    {
        dead = false;

        //set ball location
        x = location.x;
        y = location.y;

        //set ball parameters
        this.range = range;

        //set game parameters
        this.winSize = winSize;
        this.panel = panel;

        //crate vector
        vector = new Vector((float)0,-1);

        //create ball image
        speed = winSize.y/70;
        maxSpeed = speed*3;
        System.out.println("speed:" +speed);
        Ball = BitmapFactory.decodeResource(panel.getContext().getResources(), R.drawable.ball2);
    }

    @Override
    public void draw(Canvas canvas)
    {
        //draw circle
        canvas.drawBitmap(Ball, null, boundingRect(), null);
    }


    @Override
    public void update()
    {
        System.out.println("x:" + vector.getX() + " y:" +vector.getY());
        for(int i=0; i<speed; ++i)
        {
            //change ball location
            x += vector.getX();
            y += vector.getY();

            //check collision with game objects
            panel.checkCollision();


            //collision with wall
            if(x + range >= winSize.x){ vector.transformVector(HitLocation.Side);   }                   //collides with right wall
            else if(x - range <= 0){ vector.transformVector(HitLocation.Side);  }                       //collides with left wall
            else if(y + range >= winSize.y){ vector.transformVector(HitLocation.Floor); dead = true;}   //collides with bottom wall
            else if(y - range <= 0){ vector.transformVector(HitLocation.Floor);  }                      //collides with top wall
        }
    }

    @Override
    public Rect boundingRect()
    {
        Rect value = new Rect((int)(x-range), (int)(y-range), (int)(x+range), (int)(y+range));
        return value;
    }
    @Override
    public Vector getVector()
    {
        return vector;
    }
    @Override
    public void setVector(Vector vector)
    {
        this.vector = vector;
    }

    public float getRange()
    {
        return range;
    }

    public float getX()
    {
        return x;
    }

    public boolean isDead()
    {
        return dead;
    }
    public void increaseSpeed()
    {
        if(speed < maxSpeed)
        {
            ++speed;
        }
    }
}
