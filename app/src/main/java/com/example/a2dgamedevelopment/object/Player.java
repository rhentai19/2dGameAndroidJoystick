package com.example.a2dgamedevelopment.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2dgamedevelopment.GameLoop;
import com.example.a2dgamedevelopment.Joystick;
import com.example.a2dgamedevelopment.R;
import com.example.a2dgamedevelopment.Utils;

public class Player extends Circle {

    private final Joystick joystick;


    public static final double SPEED_PIXELS_PER_SECONDS = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECONDS/ GameLoop.MAX_UPS;

    public Player(Context context,Joystick joystick , double positionX, double positionY, double radius) {
        super( context,ContextCompat.getColor(context, R.color.player),positionX, positionY, radius);
        this.joystick = joystick;

    }



    public void update() {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        positionX += velocityX;
        positionY += velocityY;

        //update direction
        if (velocityX != 0 || velocityY != 0){
            double distance = Utils.getDistanceBetweenOPoins(0,0, velocityX,velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }
    }

    //public void setPosition(double positionX, double positionY) {
    //     this.positionX = positionX;
    //     this.positionY = positionY;
    // }
}
