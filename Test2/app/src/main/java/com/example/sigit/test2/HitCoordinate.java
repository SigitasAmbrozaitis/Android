package com.example.sigit.test2;

import android.graphics.Rect;

public class HitCoordinate {

    private float x;        //x coordintate shows where the ball hit paddle
    private Vector vector;
    private Rect rectangle; //hit object parameters
    boolean changed = false;//true if hit coordinate was changed

    public HitCoordinate(float x, Rect hitObject)
    {
        //setting private variables
        this.x = x;
        this.rectangle = hitObject;

        //creating new vector;
        vector = new Vector(0,0);
    }

    public void setHitCoordinate(float x, Rect hitObject)
    {
        //setting private variables
        this.x = x;
        this.rectangle = hitObject;

        //setting that object was changed
        changed = true;
    }

    public void setX(float x) {
        this.x = x;
    }

    public Vector getBounceVector()
    {
        int angle = calculateAngle();
        setVector(angle);
        return vector;
    }

    public int calculateAngle()
    {
        //find center
        float center = (rectangle.left + rectangle.right) /2;

        //move everything so that center would be zero
        float cell = (center-rectangle.left)/8;
        x -= center;
        rectangle.set((int)(rectangle.left-center),0, (int)(rectangle.right-center)  , 0);
        center = 0;

        //get what centerside the hit was in

        int angle=0;
        for(int i=1; i<9; ++i)
        {
            if(x<center)//left side
            {
                if(i*cell*(-1)< x)
                {
                    angle = i*(-1);
                    break;
                }
            }else//right side
            {
                if(i*cell> x)
                {
                    angle = i;
                    break;
                }
            }
        }
        return angle;
    }
    public void setVector(int angle)
    {
        switch(angle)
        {
            case -8:    //10
                vector.setVector(-0.97f,-0.242f);
                break;
            case -7:    //20
                vector.setVector(-0.936f,-0.351f);
                break;
            case -6:    //30
                vector.setVector(-0.857f,-0.514f);
                break;
            case -5:    //40
                vector.setVector(-0.759f,-0.65f);
                break;
            case -4:    //50
                vector.setVector(-0.639f,-0.768f);
                break;
            case -3:    //60
                vector.setVector(-0.495f, -0.868f);
                break;
            case -2:    //70
                vector.setVector(-0.351f, -0.936f);
                break;
            case -1:    //80
                vector.setVector(-0.178f,-0.984f);
                break;
            case 0:     //90
                vector.setVector(0,-1);
                break;
            case 1:     //80
                vector.setVector(0.178f,-0.984f);
                break;
            case 2:     //70
                vector.setVector(0.351f, -0.936f);
                break;
            case 3:     //60
                vector.setVector(0.495f, -0.868f);
                break;
            case 4:     //50
                vector.setVector(0.639f,-0.768f);
                break;
            case 5:     //40
                vector.setVector(0.759f,-0.65f);
                break;
            case 6:     //30
                vector.setVector(0.857f,-0.514f);
                break;
            case 7:     //20
                vector.setVector(0.936f,-0.351f);
                break;
            case 8:     //10
                vector.setVector(0.97f,-0.243f);
                break;
        }
    }

    public boolean isChanged()
    {
        return changed;
    }

    public void setChanged(boolean changed)
    {
        this.changed = changed;
    }
}


