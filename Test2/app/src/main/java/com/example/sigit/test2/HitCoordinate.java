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
                vector.setVector(-1,-0.25f);
                break;
            case -7:    //20
                vector.setVector(-1,-0.375f);
                break;
            case -6:    //30
                vector.setVector(-1,-0.6f);
                break;
            case -5:    //40
                vector.setVector(-1,-0.857f);
                break;
            case -4:    //50
                vector.setVector(-0.833f,-1);
                break;
            case -3:    //60
                vector.setVector(-0.571f, -1);
                break;
            case -2:    //70
                vector.setVector(-0.375f, -1);
                break;
            case -1:    //80
                vector.setVector(-0.181f,-1);
                break;
            case 0:     //90
                vector.setVector(0,-1);
                break;
            case 1:     //80
                vector.setVector(0.181f,-1);
                break;
            case 2:     //70
                vector.setVector(0.375f, -1);
                break;
            case 3:     //60
                vector.setVector(0.571f, -1);
                break;
            case 4:     //50
                vector.setVector(0.833f,-1);
                break;
            case 5:     //40
                vector.setVector(1,-0.857f);
                break;
            case 6:     //30
                vector.setVector(1,-0.6f);
                break;
            case 7:     //20
                vector.setVector(1,-0.375f);
                break;
            case 8:     //10
                vector.setVector(1,-0.25f);
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


