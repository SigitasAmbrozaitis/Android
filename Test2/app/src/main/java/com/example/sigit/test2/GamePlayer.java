package com.example.sigit.test2;

import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GamePlayer implements GameObject {

    private int speed = 100;
    private Rect rectangle;
    private int color;
    private Paint paint;
    private Vector vector;
    private Point winSize;


    public GamePlayer(Rect rectangle, int color, Point winSize)
    {
        this.rectangle = rectangle;
        this.color = color;
        this.winSize = winSize;
        paint = new Paint();
        paint.setColor(color);

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
        if(rectangle.centerX() < point.x)   //move right
        {
            vector.setX(1);
        }else if(rectangle.centerY()>point.x)   //move left
        {
            vector.setX(-1);
        }else   //stay in place
        {
            vector.setX(0);
        }

        for(int i=0; i<speed; ++i)
        {
            if(rectangle.centerX()==point.x){break;}
            if(point.x-rectangle.width()/2 < 0) { point.x = rectangle.width()/2; }                      //TODO fix gliching
            if(point.x+rectangle.width()/2 > winSize.x) {point.x = winSize.x - rectangle.width()/2;}    //TODO fix gliching

            Point centerPoint = new Point(rectangle.centerX()+((int)vector.getX()), rectangle.centerY());
            rectangle.set(  centerPoint.x - rectangle.width()/2 ,point.y - rectangle.height()/2, centerPoint.x +rectangle.width()/2 ,point.y + rectangle.height()/2);


        }
    }




}
