package com.example.a2dgamedevelopment;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;


    }
     public double getAverageUPS(){
        return 0;
     }

    public double getAverageFPS() {
         return 0;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();
        Canvas canvas;

        while (isRunning){

            try {
                canvas = surfaceHolder.lockCanvas();
                game.update();
                game.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
            }


        }
    }

}
