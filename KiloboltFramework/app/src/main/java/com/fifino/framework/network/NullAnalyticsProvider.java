package com.fifino.framework.network;

import org.json.JSONObject;

/**
 * NullAnalyticsProvider Analytics Provider for tracking.
 * 
 * @author porfiriopartida
 * 
 */
public class NullAnalyticsProvider implements AnalyticsProvider {
    @Override
    public void track(String event, String value) {
    }

    @Override
    public void track(String event, double value, float x, float y, float z) {
    }

    @Override
    public void track(String event, float x, float y, float z) {
    }

    @Override
    public void stop(Object args) {
    }

    @Override
    public void start(Object args) {
    }

    @Override
    public void pause(Object args) {
    }

    @Override
    public void resume(Object args) {
    }

    @Override
    public void track(String event, JSONObject obj, float value, float x,
            float y, float z) {
    }

    @Override
    public void exception(Exception ex, String caller) {
    }

    @Override
    public void error(String error, JSONObject track) {
    }
}
