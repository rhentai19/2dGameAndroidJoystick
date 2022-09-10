package com.example.a2dgamedevelopment.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2dgamedevelopment.GameLoop;
import com.example.a2dgamedevelopment.R;

public class Enemy extends Circle{
    private static final double SPAWN_PER_MINUTE = 20;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWN_PER_SECOND;
    private static  double updatesUntilNextSpawn = UPDATES_PER_SPAWN ;
    private final Player player;
    private static final double SPEED_PIXELS_PER_SECONDS = Player.SPEED_PIXELS_PER_SECONDS*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECONDS/ GameLoop.MAX_UPS;

    public Enemy(Context context, Player player) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
        Math.random()*1000,
        Math.random()*1000,
          30

        );

        this.player = player;
    }

    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;

        }else {
            updatesUntilNextSpawn --;
            return false;        }
    }

    @Override
    public void update() {
        
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        if (distanceToPlayer > 0){
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        }else {
            velocityX = 0;
            velocityY = 0;
        }

        positionX += velocityX;
        positionY += velocityY;
    }
}
