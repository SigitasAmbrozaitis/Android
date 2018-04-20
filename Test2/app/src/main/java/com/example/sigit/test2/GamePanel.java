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
    private GameHUD hud;
    private Point point;
    private Point winSize;
    //private List<GameBlock> blocks;
    private LinkedList<GameBlock> blocks;
    private Point blockSize;
    private Point nextBrickSpawnLocation;


    GamePanel(Context context, Point size)
    {
        super(context);

        //start game ticks
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        //set window size
        winSize = size;

        //setup player
        Point playerSize = new Point(winSize.x/5, winSize.y/50);
        player = new GamePlayer(new Rect(0,0,playerSize.x,playerSize.y), Color.rgb(255,0,0), winSize);
        point = new Point(winSize.x/2,winSize.y-winSize.y/10);

        //setup ball
        Point ballPoint = new Point(point.x, point.y-50);
        ball = new GameBall(ballPoint, winSize.y/100, Color.rgb(0,255,0), winSize);

        //setup HUD
        hud = new GameHUD(new Rect(0,0, winSize.x, winSize.y/5), Color.GRAY);

        //setup brick blocks
        blockSize = new Point(winSize.x/8, winSize.y/60);
        nextBrickSpawnLocation = new Point(0,winSize.y/5);

        blocks =  new LinkedList<>();
        System.out.println("window width" + winSize.x);

        for(int i = 0; i<16; ++i)
        {
            spawnBlock();
        }
        //**********************


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
        hud.draw(canvas);


        for(GameBlock block : blocks)
        {
            block.draw(canvas);
        }

    }

    public void update()
    {
        //call update on other game changing objects
        player.update(point);
        ball.update();
        hud.update();

        HitCoordinate coordinate = new HitCoordinate(0,new Rect(0,0,0,0));
        HitLocation result = HitLocation.None;

        //check collision with player
        result = checkCollision(player, ball, coordinate);
        if(result != HitLocation.None)
        {
            System.out.println(coordinate.calculateAngle());
            if(coordinate.isChanged())
            {
                Vector vector = coordinate.getBounceVector();
                //vector.addVector(player.getVector());
                ball.setVector(vector);
                coordinate.setChanged(false);
            }else
            {
                Vector vector = ball.getVector();
                vector.transformVector(result);
                ball.setVector(vector);

            }
            ball.setHit(true);
            ball.increaseSpeed();
        }

        //check collision with hud
        result = checkCollision(hud, ball, coordinate);
        if(result != HitLocation.None)
        {
            Vector vector = ball.getVector();
            vector.transformVector(result);
            ball.setVector(vector);
            ball.setHit(true);
        }

        //check colliosion with bricks
        GameBlock blockToDelete=null;
        for(GameBlock block : blocks)
        {
            block.update();
            result = checkCollision(block, ball, coordinate);
            if(result != HitLocation.None)
            {
                Vector vector = ball.getVector();
                vector.transformVector(result);
                ball.setVector(vector);
                ball.setHit(true);

                block.setRectangle(new Rect(0,0,0,0));
                blockToDelete = block;
                //blocks.remove(block);
            }


        }
        if(blockToDelete!=null)
        {
            blocks.remove(blockToDelete);
            blockToDelete = null;
        }





        //game end conditions
        if(blocks.isEmpty())
        {
            //win condition
        }
        if(ball.isDead())
        {
            //loose condition
        }

    }

    public HitLocation checkCollision(GameObject object1,GameBall object2, HitCoordinate coordinate)
    {
        HitLocation hitRegistered = HitLocation.None;
        Rect rect1 = object1.boundingRect();
        Rect rect2 = object2.boundingRect();
        float range = object2.getRange();

        float hitX = 0;

        //if ball hits wall : range going diagnoly/horizontal from center to side
        if(rect1.top-range <= rect2.top && rect1.bottom+range >= rect2.bottom && rect1.right <= rect2.right && rect1.right >= rect2.left)// 2 hit right side
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.top - range <= rect2.top && rect1.bottom +range >= rect2.bottom && rect1.left >= rect2.left && rect1.left <= rect2.right)//4 hit left side
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.top >= rect2.top && rect1.top <= rect2.bottom)//1 hit top
        {
            hitRegistered = HitLocation.Floor;
            coordinate.setHitCoordinate(object2.getX(),new Rect(rect1.left, rect1.top, rect1.right, rect1.bottom));

        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.bottom <= rect2.bottom && rect1.bottom >= rect2.top)//3 hit bot
        {
            hitRegistered = HitLocation.Floor;
        }
        //if ball hits wall: range cannot go diagnolly/horizontaly from center to side
        else if(rect1.top <= rect2.bottom && rect1.top >=rect2.top && rect1.left <= rect2.right && rect1.left >= rect2.left)
        {
            hitRegistered = HitLocation.Floor;
            //coordinate = new HitCoordinate(object2.getX(),rect1);
        }else if(rect1.top <= rect2.bottom && rect1.top >=rect2.top && rect1.right >= rect2.left && rect1.right <= rect2.right)
        {
            hitRegistered = HitLocation.Floor;
            //coordinate = new HitCoordinate(object2.getX(),rect1);
        }else if(rect1.bottom >= rect2.top && rect1.bottom <=rect2.bottom && rect1.left <= rect2.right && rect1.left >= rect2.left)
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.bottom >= rect2.top && rect1.bottom <=rect2.bottom && rect1.right >= rect2.left && rect1.right <= rect2.right)
        {
            hitRegistered = HitLocation.Side;
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
