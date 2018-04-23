package com.example.sigit.test2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameBlock implements GameObject {

    private Vector vector;  //block vector, TODO delete if bounce vector does not add moving vector
    private Rect rectangle; //block size parameters and laction
    private Bitmap Block;

    public GameBlock(Rect rectangle, Context context)
    {
        //set private variables
        this.vector = new Vector(0,0);
        this.rectangle = rectangle;

        Block = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick2);


    }
    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(Block, null, rectangle, null);
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
