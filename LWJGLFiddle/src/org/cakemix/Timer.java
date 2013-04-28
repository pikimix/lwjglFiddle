package org.cakemix;

import org.lwjgl.Sys;

/**
 *
 * @author cakemix
 */
public class Timer {
    
    // set timer vars
    // last time the delta was updated
    private long lastTick = 0;
    private int delta = 0;
    public Timer(){
        lastTick = Sys.getTime();
    }
    
    public void update(){
        long tick = getTime();
        delta = (int) ( tick - lastTick );
        lastTick = tick;
    }
    
    public long getTime(){
        return Sys.getTime();
    }
    
    public int getDelta(){
        return delta;
    }
}
