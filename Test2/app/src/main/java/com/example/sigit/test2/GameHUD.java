package com.example.sigit.test2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.sigit.test2.GameObject;
import com.example.sigit.test2.Vector;

public class GameHUD implements GameObject {

    private Rect rectangle; //hud location and size parameters
    private Paint textPaint;//hud text paint
    private int textColor;  //hud text color
    private int score=0;    //game score
    private Bitmap hudBackground;
    private int lives =0;



    public GameHUD(Rect rectangle, Context context)
    {
        //set loaction and color
        this.rectangle = rectangle;

        hudBackground =  BitmapFactory.decodeResource(context.getResources(), R.drawable.hudbackground);

        //create text paint and set its parameters
        textColor = Color.rgb(255,0,0);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);

    }

    @Override
    public void draw(Canvas canvas)
    {

        canvas.drawBitmap(hudBackground, null, rectangle, null);

        canvas.drawText("Points: " + score, rectangle.right-200,rectangle.bottom/4*2, textPaint);
        canvas.drawText("Lives : " + lives, rectangle.right-200, rectangle.bottom/4*3, textPaint);

    }

    @Override
    public void update() {

    }

    @Override
    public Rect boundingRect()
    {
        return rectangle;
    }


    public void setScore(int score)
    {
        this.score = score;
    }
    public void setLives(int lives)
    {
        this.lives = lives;
    }

}

