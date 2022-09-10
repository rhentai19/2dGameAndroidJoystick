package com.example.a2dgamedevelopment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private  final int outerCircleCenterPositionX,outerCircleCenterPositionY;
    private  int innerCircleCenterPositionX,innerCircleCenterPositionY;
    private final int outerCircleRadius,innerCircleRadius;
    private final Paint outerCirclePaint, innerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX,actuatorY;

    public Joystick(int centerPositionX,int centerPositionY, int outerCircleRadius,int innerCircleRadius ){
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        this.outerCircleRadius =outerCircleRadius;
        this.innerCircleRadius =innerCircleRadius;

        //Circle Paint
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //Circle Paint
        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint);
        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint);
    }

    public void update(){
        updateInnerCirclePosition();

    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Utils.getDistanceBetweenOPoins(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                touchPositionX,
                touchPositionY
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Utils.getDistanceBetweenOPoins(0,0, deltaX,deltaY);

        if (deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaX/deltaDistance;
        }

    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }
    public double getActuatorY() {
        return actuatorY;
    }
}
