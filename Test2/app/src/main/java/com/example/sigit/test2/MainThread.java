package com.example.sigit.test2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{

    public static final int MAX_FPS =  30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run()
    {
        long startTime;
        long waitTime;
        long timeMillis = 1000/MAX_FPS;
        long targetTime = 1000/MAX_FPS;
        long totalTime = 0;
        short frameCount = 0;

        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch(Exception ex) {  ex.printStackTrace();  }
            finally
            {
                if(canvas !=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception ex){ ex.printStackTrace();  }
                }
            }

            timeMillis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try
            {
                if(waitTime>0)
                {
                    sleep(waitTime);
                }
            }catch(Exception ex){ ex.printStackTrace(); }

            totalTime += System.nanoTime() - startTime;
            ++frameCount;
            if(frameCount == MAX_FPS)
            {
                averageFPS = 1000/(totalTime/frameCount/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }

        }
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

}
