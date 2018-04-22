package com.example.sigit.test2;

import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GamePlayer implements GameObject {

    private int speed = 80; //paddle speed
    private Rect rectangle; //player size and location parameters
    private int color;      //player paint color
    private Paint paint;    //player paint
    private Vector vector;  //player movement vector
    private Point winSize;  //device screen parameters


    public GamePlayer(Rect rectangle, int color, Point winSize)
    {
        //set private variables
        this.rectangle = rectangle;
        this.color = color;
        this.winSize = winSize;

        //create new paint
        paint = new Paint();
        paint.setColor(color);

        //create new vector
        vector = new Vector(0,0);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRect(rectangle,paint);
    }

    @Override
    public void update()
    {

    }
    @Override
    public Rect boundingRect()
    {
        return rectangle;
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

    public void update(Point point)
    {
        //set movement vector
        if(rectangle.centerX() < point.x)       //move right
        {
            vector.setX(1);
        }else if(rectangle.centerY()>point.x)   //move left
        {
            vector.setX(-1);
        }else                                   //stay in place
        {
            vector.setX(0);
        }

        //move paddle
        for(int i=0; i<speed; ++i)
        {
            if(rectangle.centerX()==point.x){break;}                                                            //paddle is where he needs to be
            if(point.x-rectangle.width()/2 < 0) { point.x = rectangle.width()/2; break;}                        //paddle is at left side, cant move more
            if(point.x+rectangle.width()/2 > winSize.x) {point.x = winSize.x - rectangle.width()/2; break;}     //paddle is at right side, cant move more

            //set paddle new location
            Point centerPoint = new Point(rectangle.centerX()+((int)vector.getX()), rectangle.centerY());
            rectangle.set(  centerPoint.x - rectangle.width()/2 ,point.y - rectangle.height()/2, centerPoint.x +rectangle.width()/2 ,point.y + rectangle.height()/2);
        }
    }




}
