package com.fifino.framework.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * DEBUG Analytics Provider for tracking.
 * 
 * @author porfiriopartida
 * 
 */
public class DebugAnalyticsProvider implements AnalyticsProvider {
    boolean on = true;
    @Override
    public void track(String event, String value) {
        if (value.length() > 0) {
            Log.i(event, value);
        } else {
            Log.i(event, "");
        }
    }

    @Override
    public void track(String event, double value, float x, float y, float z) {
        this.track(event, new JSONObject(), -1, x, y, z);
    }

    @Override
    public void track(String event, float x, float y, float z) {
        this.track(event, -1, x, y, z);
    }

    @Override
    public void stop(Object args) {
        this.on = false;
    }

    @Override
    public void start(Object args) {
        this.on = true;
    }

    @Override
    public void pause(Object args) {
        this.stop(args);
    }

    @Override
    public void resume(Object args) {
        this.start(args);
    }

    @Override
    public void track(String event, JSONObject obj, float value, float x,
            float y, float z) {
        Log.i(event, x + "," + y);
    }

    @Override
    public void exception(Exception ex, String caller) {
        Log.e("Exception: ", caller);
        ex.printStackTrace();
    }

    @Override
    public void error(String error, JSONObject track) {
        System.err.println("Error: " + error);
        Log.e("Error: ", error);
        try {
            System.err.println(track.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
