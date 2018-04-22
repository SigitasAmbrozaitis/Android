package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameBlock implements GameObject {

    private Vector vector;  //block vector, TODO delete if bounce vector does not add moving vector
    private Rect rectangle; //block size parameters and laction
    private int color;      //block color TODO delete if image is used
    private Point winSize;  //device window size
    private Paint paint;    //block pait

    public GameBlock(Rect rectangle, int color, Point winSize)
    {
        //set private variables
        this.vector = new Vector(0,0);
        this.rectangle = rectangle;
        this.color =color;
        this.winSize = winSize;

        //create new paint
        paint = new Paint();
        paint.setColor(color);
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

    public void setRectangle(Rect rect)
    {
        rectangle = rect;
    }
}
