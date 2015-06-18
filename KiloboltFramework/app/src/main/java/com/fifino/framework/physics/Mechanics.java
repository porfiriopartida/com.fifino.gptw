package com.fifino.framework.physics;


public class Mechanics {

    public static float GRAVITY = -26f;
    public static float MAX_SPEED = 250f;

    public static double getSpeed(double time, float currentSpeed) {
        double speed = currentSpeed - Mechanics.GRAVITY * time;
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < -MAX_SPEED) {
            speed = -MAX_SPEED;
        }
        return speed;
    }
}
