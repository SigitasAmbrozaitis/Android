package com.example.sigit.test2;



public class Vector {
    private float x;
    private float y;

    public Vector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void transformVector(HitLocation location)
    {
        if(location == HitLocation.Side){ x = x * (-1); }
        if(location == HitLocation.Floor){y = y * (-1); }
    }
    public void converToSingle()
    {
        float max = 0;
        if(x < y)
        {
            max = y;
        }else
        {
            max = x;
        }
        if(max>1)
        {
            x = x/max;
            y = y/max;
        }
    }
    public void addVector(Vector vector)
    {
        this.x += vector.getX();
        this.y += vector.getY();
        //converToSingle();
    }
}
