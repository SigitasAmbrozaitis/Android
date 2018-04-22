package com.example.sigit.test2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.sigit.test2.GameObject;
import com.example.sigit.test2.Vector;

public class GameHUD implements GameObject {

    private Rect rectangle; //hud location and size parameters
    private int color;      //hud base color TODO delete if image is used
    private Paint paint;    //hud base paint
    private Paint textPaint;//hud text paint
    private int textColor;  //hud text color
    private int score=0;    //game score
    private static int level;//game level TODO implement




    public GameHUD(Rect rectangle)
    {
        //set loaction and color
        this.rectangle = rectangle;
        this.color = Color.rgb(0,0,0);

        //increase level TODO doesnt work
        GameHUD.increaseLevel();

        //create base paint and set its parameters
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);

        //create text paint and set its parameters
        textColor = Color.rgb(255,0,0);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRect(rectangle, paint);
        canvas.drawText("Ponts: " + score, rectangle.right/2,rectangle.bottom/4*3, textPaint);
        canvas.drawText("Level: " + level, rectangle.right/2, rectangle.bottom/4, textPaint);

    }

    @Override
    public void update() {

    }

    @Override
    public Rect boundingRect()
    {
        return rectangle;
    }

    @Override
    public void setVector(Vector vector) {

    }

    @Override
    public Vector getVector() {

        return new Vector(0,0);
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public static void increaseLevel()
    {
        ++level;
    }
    public static void decreseLevel()
    {
        --level;
    }
}

