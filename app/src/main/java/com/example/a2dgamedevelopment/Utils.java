package com.example.a2dgamedevelopment;

public class Utils {

    public static double getDistanceBetweenOPoins(double p1x,double p1y, double p2x, double p2y) {
        return Math.sqrt(
                Math.pow(p1x - p2x, 2)+
                Math.pow(p1y - p2y, 2)
        );

    }
}
