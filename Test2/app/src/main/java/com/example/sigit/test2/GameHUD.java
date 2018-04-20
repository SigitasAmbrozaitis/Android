package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.sigit.test2.GameObject;
import com.example.sigit.test2.Vector;

public class GameHUD implements GameObject {

    private Rect rectangle;
    private int color;
    private Paint paint;


    public GameHUD(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;

        paint = new Paint(color);


    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    @Override
    public Rect boundingRect() {
        return rectangle;
    }

    @Override
    public void setVector(Vector vector) {

    }

    @Override
    public Vector getVector() {

        return null;
    }
}

