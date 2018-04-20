package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
    public Rect boundingRect();
    public Vector getVector();
    public void setVector(Vector vector);

}
