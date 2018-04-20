package com.example.sigit.test2;

import android.graphics.Rect;

public class HitCoordinate {

    private float x;
    private Vector vector;
    private Rect rectangle;
    boolean changed = false;

    public HitCoordinate(float x, Rect hitObject)
    {

       this.x = x;
       this.rectangle = hitObject;
       vector = new Vector(0,0);
    }

    public void setHitCoordinate(float x, Rect hitObject)
    {
        this.x = x;
        this.rectangle = hitObject;
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

        System.out.println("cell: " + cell + "hit: " + x + " rectangle " + rectangle.left + " " + rectangle.right + " center " + center);

        //get what centerside the hit was in
        float factor = 1;
        if(x<center) { factor = -1;}

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
                if(i*cell< x)
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
            case -8:
                vector.setVector(-1,-0.25f);
                break;
            case -7:
                vector.setVector(-1,-0.375f);
                break;
            case -6:
                vector.setVector(-1,-0.6f);
                break;
            case -5:
                vector.setVector(-1,-0.857f);
                break;
            case -4:
                vector.setVector(-0.833f,-1);
                break;
            case -3:
                vector.setVector(-0.571f, -1);
                break;
            case -2:
                vector.setVector(-0.375f, -1);
                break;
            case -1:
                vector.setVector(-0.181f,-1);
                break;
            case 0:
                vector.setVector(0,-1);
                break;
            case 1:
                vector.setVector(0.181f,-1);
                break;
            case 2:
                vector.setVector(0.375f, -1);
                break;
            case 3:
                vector.setVector(0.571f, -1);
                break;
            case 4:
                vector.setVector(0.833f,-1);
                break;
            case 5:
                vector.setVector(1,-0.857f);
                break;
            case 6:
                vector.setVector(1,-0.6f);
                break;
            case 7:
                vector.setVector(1,-0.375f);
                break;
            case 8:
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


