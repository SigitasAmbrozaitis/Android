package com.example.sigit.test2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private GamePlayer player;
    private GameBall ball;
    private Point point;
    private Point winSize;
    //private List<GameBlock> blocks;
    private LinkedList<GameBlock> blocks;
    private Point blockSize;
    private Point nextBrickSpawnLocation;


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

        blockSize = new Point(winSize.x/8, winSize.y/60);
        nextBrickSpawnLocation = new Point(0,0);

        blocks =  new LinkedList<>();
        System.out.println("window width" + winSize.x);

        for(int i = 0; i<16; ++i)
        {
            spawnBlock();
        }
        Rect test = player.boundingRect();
        System.out.println("brick: left top " + test.left + " " + test.top +  " right bot " + test.right +" " + test.bottom);

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
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        ball.draw(canvas);


        for(GameBlock block : blocks)
        {
            block.draw(canvas);
        }

    }

    public void update()
    {
        player.update(point);
        ball.update();

        HitLocation result = HitLocation.None;
        result = checkCollision(player, ball);
        if(result != HitLocation.None)
        {
            Vector vector = ball.getVector();
            vector.transformVector(result);
            ball.setVector(vector);
        }

        for(GameBlock block : blocks)
        {
            result = checkCollision(block, ball);
            if(result != HitLocation.None)
            {
                Vector vector = ball.getVector();
                vector.transformVector(result);
                ball.setVector(vector);

                block.setRectangle(new Rect(0,0,0,0));
                blocks.remove(block);
            }
        }

    }

    public HitLocation checkCollision(GameObject object1,GameBall object2)
    {
        HitLocation hitRegistered = HitLocation.None;
        Rect rect1 = object1.boundingRect();
        Rect rect2 = object2.boundingRect();
        float range = object2.getRange();

        float hitX = 0;

        //if ball hits wall : range going diagnoly/horizontal from center to side
        if(rect1.top-range <= rect2.top && rect2.bottom+range >= rect2.bottom && rect1.right <= rect2.right && rect1.right >= rect2.left)// 2 hit right side
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.top - range <= rect2.top && rect2.bottom +range >= rect2.bottom && rect1.left >= rect2.left && rect1.left <= rect2.right)//4 hit left side
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.top >= rect2.top && rect1.top <= rect2.bottom)//1 hit top
        {
            hitRegistered = HitLocation.Floor;
            hitX = object2.getX();
        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.bottom <= rect2.bottom && rect1.bottom >= rect2.top)//3 hit bot
        {
            hitRegistered = HitLocation.Floor;
        }
        //if ball hits wall: range cannot go diagnolly/horizontaly from center to side
        else if(rect1.top <= rect2.bottom && rect1.top >=rect2.top && rect1.left <= rect2.right && rect1.left >= rect2.left)
        {
            //hitRegistered = HitLocation.Floor;
            hitX = object2.getX();
        }else if(rect1.top <= rect2.bottom && rect1.top >=rect2.top && rect1.right >= rect2.left && rect1.right <= rect2.right)
        {
            //hitRegistered = HitLocation.Floor;
            hitX = object2.getX();
        }else if(rect1.bottom >= rect2.top && rect1.bottom <=rect2.bottom && rect1.left <= rect2.right && rect1.left >= rect2.left)
        {
            //hitRegistered = HitLocation.Side;
        }else if(rect1.bottom >= rect2.top && rect1.bottom <=rect2.bottom && rect1.right >= rect2.left && rect1.right <= rect2.right)
        {
            //hitRegistered = HitLocation.Side;
        }

        return hitRegistered;
    }

    public void spawnBlock()
    {
        Rect spawnRectangle = new Rect(nextBrickSpawnLocation.x, nextBrickSpawnLocation.y, nextBrickSpawnLocation.x+blockSize.x, nextBrickSpawnLocation.y+blockSize.y);
        //System.out.println("brick location: " + nextBrickSpawnLocation.x + " " + nextBrickSpawnLocation.y);
        //System.out.println("brick size:" + blockSize.x + " " + blockSize.y);


        GameBlock block = new GameBlock(spawnRectangle, Color.BLUE, winSize);
        //Rect test = block.boundingRect();
        //System.out.println("brick: left top " + test.left + " " + test.top +  " right bot " + test.right +" " + test.bottom);

        blocks.addLast(block);
        System.out.println(blocks.size());

        if(nextBrickSpawnLocation.x + blockSize.x <= winSize.x)
        {
            nextBrickSpawnLocation.set(nextBrickSpawnLocation.x + blockSize.x, nextBrickSpawnLocation.y);
        }else
        {
            nextBrickSpawnLocation.set(0, nextBrickSpawnLocation.y+blockSize.y);
        }



    }

}
