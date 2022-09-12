package com.example.a2dgamedevelopment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2dgamedevelopment.object.Circle;
import com.example.a2dgamedevelopment.object.Enemy;
import com.example.a2dgamedevelopment.object.Player;
import com.example.a2dgamedevelopment.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Joystick joystick;
    //private final Enemy enemy;
    private final GameLoop gameloop;
    private final Player player;
    private final List<Enemy> enemyList = new ArrayList<>();
    private final List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellToCast = 0;

    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameloop = new GameLoop(this, surfaceHolder);

        joystick = new Joystick(200,580,100,70);
        player = new Player(getContext(),joystick,650,325,30);

        
        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()){
                    numberOfSpellToCast++ ;

                }else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);

                }else {
                    numberOfSpellToCast++ ;
                }
                return  true;

            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return  true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())){
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }

                return  true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameloop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        for (Enemy enemy : enemyList){
            enemy.draw(canvas);
        }
        for (Spell spell : spellList){
            spell.draw(canvas);
        }
    }

    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameloop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("UPS: " + averageUPS, 100, 30, paint);
    }
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameloop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("FPS: " + averageFPS, 100, 80, paint);
    }

    public void update() {
        joystick.update();
        player.update();

        if (Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }

        while (numberOfSpellToCast > 0){
            spellList.add(new Spell(getContext(),player));
            numberOfSpellToCast -- ;
        }

        for (Enemy enemy : enemyList){
            enemy.update();
        }
        for (Spell spell : spellList){
            spell.update();
        }

        //Collision
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()){
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)){
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()){
                Circle spell = iteratorSpell.next();

                if (Circle.isColliding(spell, enemy)){
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
    }
}
