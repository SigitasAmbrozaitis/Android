package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameBall implements GameObject {

    private boolean dead;
    private float x;
    private float y;
    private float range;
    private int color;
    private Paint paint;
    private Vector vector;
    private int speed = 20;
    private int maxSpeed=60;
    private Point winSize;
    private boolean hit = false;
    private GamePanel panel;

    public GameBall(Point location, float range, int color, Point winSize, GamePanel panel)
    {
        dead = false;

        x = location.x;
        y = location.y;
        this.range = range;
        this.color = color;
        this.winSize = winSize;
        this.panel = panel;

        paint = new Paint();
        paint.setColor(this.color);

        vector = new Vector((float)-0.5,-1);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, range, paint);

    }


    @Override
    public void update()
    {
        for(int i=0; i<speed; ++i)
        {
            x += vector.getX();
            y += vector.getY();

            //collision with wall
            panel.checkCollision();

            if(x + range >= winSize.x){ vector.transformVector(HitLocation.Side);   }       //collides with right wall
            else if(x - range <= 0){ vector.transformVector(HitLocation.Side);  }           //collides with left wall
            else if(y + range >= winSize.y){ vector.transformVector(HitLocation.Floor); dead = true;}  //collides with bottom wall
            else if(y - range <= 0){ vector.transformVector(HitLocation.Floor);  }          //collides with top wall
            //TODO implement for break if colliion is detected

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

    public float getX() {
        return x;
    }

    public boolean isDead() {
        return dead;
    }
    public void increaseSpeed()
    {
        if(speed < maxSpeed)
        {
            ++speed;
        }
    }

    public void setHit(boolean hit)
    {
        this.hit = hit;
    }
}
