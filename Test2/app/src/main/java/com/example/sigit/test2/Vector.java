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
        //TODO implement or delete
    }
    public void addVector(Vector vector)
    {
        this.x += vector.getX();
        this.y += vector.getY();
        converToSingle();
        //TODO delte if not used
    }
    public void setVector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    public void setVector(Vector vector)
    {
        this.x = vector.x;
        this.y = vector.y;
    }
}
