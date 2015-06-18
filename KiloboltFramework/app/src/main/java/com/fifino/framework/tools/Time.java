package com.fifino.framework.tools;

import java.util.Calendar;


public class Time {

    public static double getCurrentTime(){
        return Calendar.getInstance().getTimeInMillis()/1000d;
    }
}
