package com.example.a2dgamedevelopment;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double avergadeUPS;
    private double avergadeFPS;
    private static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;


    }
     public double getAverageUPS(){

        return avergadeUPS;
     }

    public double getAverageFPS() {

        return avergadeFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //GameLoopCanvas
        Canvas canvas = null;

        startTime = System.currentTimeMillis();

        while (isRunning){

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    game.update();
                    game.draw(canvas);
                    updateCount++;
                }
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
            }finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);

            if (sleepTime > 0){
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (sleepTime <0 && updateCount < MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000){
                avergadeUPS = updateCount / (1E-3 * elapsedTime);
                avergadeFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;






                startTime = System.currentTimeMillis();
            }


        }
    }

}
