package com.fifino.framework.network;

import org.json.JSONObject;

/**
 * 
 * @author porfiriopartida
 *
 */
public interface AnalyticsProvider {
    
    /**
     * Tracks an generic event
     * @param event the event to track
     * @param value A generic value if required 
     */
    public void track(String event, String value);
    /**
     * 
     * Tracks an event
     * @param event the event to track
     * @param level The level of the game
     * @param value The value of the event
     * @param x X coord where the event happened
     * @param y Y coord where the event happened
     * @param z Z coord where the event happened
     */
    public void track(String event, JSONObject args, float value, float x, float y, float z);
    /**
     * 
     * Tracks an event
     * @param event the event to track
     * @param value The value of the event
     * @param x X coord where the event happened
     * @param y Y coord where the event happened
     * @param z Z coord where the event happened
     */
    public void track(String event, double value, float x, float y, float z);
    /**
     * 
     * Tracks an event
     * @param event the event to track
     * @param x X coord where the event happened
     * @param y Y coord where the event happened
     * @param z Z coord where the event happened
     */
    public void track(String event, float x, float y, float z);

    /**
     * Stops the tracking
     * @param args args if needed
     */
    public void stop(Object args);
    /**
     * Starts the tracking
     * @param args args if needed
     */
    public void start(Object args)
    /**
     * Pauses the tracking
     * @param args args if needed
     */;
    public void pause(Object args);
    /**
     * Resume the tracking
     * @param args args if needed
     */;
    public void resume(Object args);
    /**
     * Tracks an generic event
     * @param event the event to track
     * @param value A generic value if required 
     */
    public void exception(Exception ex, String caller);
    /**
     * Tracks an generic event
     * @param event the event to track
     * @param value A generic value if required 
     */
    public void error(String error, JSONObject track);
}