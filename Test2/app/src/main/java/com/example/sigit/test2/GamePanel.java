package com.example.sigit.test2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private Rect test; //TODO delete (used for collision visualization)

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
        //player = new GamePlayer(new Rect(0,0,400,200), Color.rgb(255,0,0), winSize);
        point = new Point(winSize.x/2,winSize.y-winSize.y/10);

        Point ballPoint = new Point(point.x, point.y-50);
        ball = new GameBall(ballPoint, winSize.y/50, Color.rgb(255,255,0), winSize);

        test = new Rect(0,0,0,0); //TODO delete

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

        //TODO delete block
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(test, paint);
        //block ends here
    }

    public void update()
    {
        player.update(point);
        ball.update();
        checkCollision(player, ball);

    }

    public void checkCollision(GameObject object1,GameBall object2)
    {
        Rect rect1 = object1.boundingRect();
        Rect rect2 = object2.boundingRect();
        Rect result = new Rect(0,0,0,0);

        Vector vector1 = object1.getVector();
        Vector vector2 = object2.getVector();

        float range = object2.getRange();


        if(rect1.top-range <= rect2.top && rect2.bottom+range >= rect2.bottom && rect1.right <= rect2.right && rect1.right >= rect2.left)// 2 hit right side
        {
            vector2.transformVector(HitLocation.Side);
            //vector2.addVector(vector1);
            object2.setVector(vector2);
            System.out.println("hit right");
        }else if(rect1.top - range <= rect2.top && rect2.bottom +range >= rect2.bottom && rect1.left >= rect2.left && rect1.left <= rect2.right)//4 hit left side
        {
            vector2.transformVector(HitLocation.Side);
            //vector2.addVector(vector1);
            object2.setVector(vector2);
            System.out.println("hit left");
        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.top >= rect2.top && rect1.top <= rect2.bottom)//1 hit top
        {
            vector2.transformVector(HitLocation.Floor);
            //vector2.addVector(vector1);
            object2.setVector(vector2);
            System.out.println("hit top");
        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.bottom <= rect2.bottom && rect1.bottom >= rect2.top)//3 hit bot
        {
            vector2.transformVector(HitLocation.Floor);
            //vector2.addVector(vector1);
            object2.setVector(vector2);
            System.out.println("hit bot");
        }



        /*//if rect1 is first
        if(checkRectangleCollision(rect1, rect2, result))
        {
            System.out.println(result.left + " " + result.top + " " + result.right + " "+ result.bottom);
        }
        //else rect2 is first
        //
        else if(checkRectangleCollision(rect2,rect1, result))
        {
            System.out.println(result.left + " " + result.top + " " + result.right + " "+ result.bottom);
        }else
        {
            result.set(0,0,0,0);
        }
        test = result; //TODO delete
        */
    }

    public boolean checkRectangleCollision(Rect rect1, Rect rect2, Rect result)
    {
        boolean valueToReturn = false;
        if(rect1.left < rect2.left && rect1.top < rect2.top )
        {
            if(rect2.top >= rect1.top && rect2.top <=rect1.bottom) //y axis collides
            {
                if(rect2.left >= rect1.left && rect2.left <= rect1.right)//x axis collides
                {
                    result.set(rect2.left, rect2.top, rect1.right, rect1.bottom);
                    valueToReturn = true;
                }
            }
        }
        return valueToReturn;
    }


}
