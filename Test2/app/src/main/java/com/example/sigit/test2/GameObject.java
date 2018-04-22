package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GameObject {
    public void draw(Canvas canvas);    //methods that draws object on view
    public void update();               //method that handles everything that need to be done every frame, except drawing
    public Rect boundingRect();         //return object bounding rectange used in collision
    public Vector getVector();          //returns object movement vector
    public void setVector(Vector vector);//set object movement vector

}
