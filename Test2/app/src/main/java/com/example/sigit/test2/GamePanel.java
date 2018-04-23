package com.example.sigit.test2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;    //game activiti, used for opening new activities or closing this
    private MainThread thread;  //game thread
    private GamePlayer player;  //player paddle
    private GameBall ball;      //game ball
    private GameHUD hud;        //hud
    private Point point;        //point used to set player paddle location
    private Point winSize;      //device screen parameters
    private LinkedList<GameBlock> blocks;   //container of game blocks
    private Point blockSize;                //block size parameters
    private Point nextBrickSpawnLocation;   //next block spawn parameters
    private int score=0;        //game score
    private int blockWorth=100; //block worth
    private int blockNumber= 24;//block number, how many there are generated
    private Bitmap Background;


    GamePanel(Context context, Point size)
    {
        super(context);

        //start game ticks
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        //set game parameters
        winSize = size;
        this.context = context;

        //setup player
        Point playerSize = new Point(winSize.x/5, winSize.y/50);
        player = new GamePlayer(new Rect(0,0,playerSize.x,playerSize.y), winSize, context);
        point = new Point(winSize.x/2,winSize.y-winSize.y/7);

        //setup ball
        Point ballPoint = new Point(point.x, point.y-50);
        ball = new GameBall(ballPoint, winSize.y/100, winSize, this);

        //setup HUD
        hud = new GameHUD(new Rect(0,0, winSize.x, winSize.y/5), context);

        //setup brick blocks
        blockSize = new Point(winSize.x/8, winSize.y/60);
        nextBrickSpawnLocation = new Point(0,winSize.y/5);
        blocks =  new LinkedList<>();
        for(int i = 0; i<blockNumber; ++i)
        {
            spawnBlock();
        }

        //setup background image
        Background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //launch main thread
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //stop thread untill its stopped
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
        //handle player input
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                point.set((int)event.getX(), winSize.y-winSize.y/7);
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas)
    {
        //redraw all objects in view
        super.draw(canvas);

        canvas.drawBitmap(Background, 0 , 0 ,null);

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

        //set hud score
        hud.setScore(score);

        //game end conditions
        if(blockNumber == 0)
        {
            //stop thread
            this.thread.setRunning(false);

            //open new window
            Intent GameIntent = new Intent(context, ContinueActivity.class);
            context.startActivity(GameIntent);

            //close old window
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        //loose condition
        if(ball.isDead())
        {
            //stop thread
            this.thread.setRunning(false);

            //open new window
            Intent GameIntent = new Intent(context, LooseActivity.class);
            context.startActivity(GameIntent);

            //close old window
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }
    public void checkCollision()
    {
        HitCoordinate coordinate = new HitCoordinate(0,new Rect(0,0,0,0));
        HitLocation result;
        //check collision with player
        result = checkObjectCollision(player, ball, coordinate);

        //handle collision with player
        if(result != HitLocation.None)
        {
            //if hits paddle top
            if(coordinate.isChanged())
            {
                Vector vector = coordinate.getBounceVector();
                ball.setVector(vector);
                coordinate.setChanged(false);
            }
            //if hits soething else
            else
            {
                Vector vector = ball.getVector();
                vector.transformVector(result);
                ball.setVector(vector);

            }
            //increase ball speed
            ball.increaseSpeed();
        }


        //check collision with hud
        result = checkObjectCollision(hud, ball, coordinate);

        //handle collision with hud
        if(result != HitLocation.None)
        {
            //mirror ball vector if hit
            Vector vector = ball.getVector();
            vector.transformVector(result);
            ball.setVector(vector);
        }

        //check collision with bricks
        GameBlock blockToDelete=null;
        for(GameBlock block : blocks)
        {
            result = checkObjectCollision(block, ball, coordinate);
            if(result != HitLocation.None)
            {
                //mirror ball vector if hit
                Vector vector = ball.getVector();
                vector.transformVector(result);
                ball.setVector(vector);

                //set block to delete
                blockToDelete = block;
                break;
            }
        }
        //delete set block
        if(blockToDelete!=null)
        {
            score += blockWorth;
            blockToDelete.setRectangle(new Rect(0,0,0,0));
            blocks.remove(blockToDelete);
            --blockNumber;
            blockToDelete = null;
        }

    }


    public HitLocation checkObjectCollision(GameObject object1,GameBall object2, HitCoordinate coordinate)
    {
        HitLocation hitRegistered = HitLocation.None;
        Rect rect1 = object1.boundingRect();
        Rect rect2 = object2.boundingRect();
        float range = object2.getRange();

        //if ball hits wall : range going diagnoly/horizontal from center to side
        if(rect1.top <= rect2.top && rect1.bottom >= rect2.bottom && rect1.right <= rect2.right && rect1.right >= rect2.left)//hit right side
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.top <= rect2.top && rect1.bottom >= rect2.bottom && rect1.left >= rect2.left && rect1.left <= rect2.right)//hit left side
        {
            hitRegistered = HitLocation.Side;
        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.top >= rect2.top && rect1.top <= rect2.bottom)//hit top
        {
            hitRegistered = HitLocation.Floor;
            coordinate.setHitCoordinate(object2.getX(),new Rect(rect1.left, rect1.top, rect1.right, rect1.bottom));

        }else if(rect1.left-range <= rect2.left && rect1.right+range >= rect2.right && rect1.bottom <= rect2.bottom && rect1.bottom >= rect2.top)//hit bot
        {
            hitRegistered = HitLocation.Floor;
        }
        //if ball hits wall: range cannot go diagnolly/horizontaly from center to side
        else if(rect1.top <= rect2.bottom && rect1.top >=rect2.top && rect1.left <= rect2.right && rect1.left >= rect2.left)
        {
            hitRegistered = HitLocation.Floor;
        }else if(rect1.top <= rect2.bottom && rect1.top >=rect2.top && rect1.right >= rect2.left && rect1.right <= rect2.right)
        {
            hitRegistered = HitLocation.Floor;
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
        //spawn new block
        Rect spawnRectangle = new Rect(nextBrickSpawnLocation.x, nextBrickSpawnLocation.y, nextBrickSpawnLocation.x+blockSize.x, nextBrickSpawnLocation.y+blockSize.y);
        GameBlock block = new GameBlock(spawnRectangle, context);
        blocks.addLast(block);

        //calculate next block position
        if(nextBrickSpawnLocation.x + blockSize.x < winSize.x)
        {
            nextBrickSpawnLocation.set(nextBrickSpawnLocation.x + blockSize.x, nextBrickSpawnLocation.y);
        }else
        {
            nextBrickSpawnLocation.set(0, nextBrickSpawnLocation.y+blockSize.y);
        }

    }

}
