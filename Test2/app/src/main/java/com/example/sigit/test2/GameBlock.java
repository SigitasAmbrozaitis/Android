package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameBlock implements GameObject {

    private Vector vector;
    private Rect rectangle;
    private int color;
    private Point winSize;
    private Paint paint;

    public GameBlock(Rect rectangle, int color, Point winSize)
    {
        this.vector = new Vector(0,0);
        this.rectangle = rectangle;
        this.color =color;
        this.winSize = winSize;

        paint = new Paint();
        paint.setColor(color);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rectangle,paint);
    }

    @Override
    public void update() {

    }

    @Override
    public Rect boundingRect() {
        return rectangle;
    }

    @Override
    public Vector getVector() {
        return vector;
    }

    @Override
    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public void setLocation(int x, int y)
    {
        rectangle.set(x, y , x+rectangle.width(), y+rectangle.height());
    }
    public void setRectangle(Rect rect)
    {
        rectangle = rect;
    }
}
