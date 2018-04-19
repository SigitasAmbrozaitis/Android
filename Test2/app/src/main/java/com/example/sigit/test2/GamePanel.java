package com.example.sigit.test2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private GamePlayer player;
    private GameBall ball;
    private Point point;
    private Point winSize;

    GamePanel(Context context, Point size)
    {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        //
        winSize = size;
        Point playerSize = new Point(winSize.x/5, winSize.y/50);
        player = new GamePlayer(new Rect(0,0,playerSize.x,playerSize.y), Color.rgb(255,0,0), winSize);
        point = new Point(winSize.x/2,winSize.y-winSize.y/10);

        Point ballPoint = new Point(point.x, point.y-50);
        ball = new GameBall(ballPoint, winSize.y/50, Color.rgb(255,255,0), winSize);



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                point.set((int)event.getX(), winSize.y-winSize.y/10);
                System.out.println(event.getX());
                break;
        }

        return true;
        //return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        ball.draw(canvas);
    }

    public void update()
    {
        player.update(point);
        ball.update();
    }

}
