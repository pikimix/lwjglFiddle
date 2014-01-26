package org.cakemix;

import org.lwjgl.Sys;

/**
 *
 * @author cakemix
 */
public class Timer {
    
    // set timer vars
    // last time the delta was updated
    private long lastTick = 0, lastFrame=0;
    private int delta = 0, frames = 0, fps = 0;
    public Timer(){
        System.out.println(Sys.getTimerResolution());
        lastTick = Sys.getTime();
    }
    
    public void update(){
        long tick = getTime();
        delta = (int) ( tick - lastTick );
        lastTick = tick;
        frames++;
        if (tick - lastFrame >= 1000)
        {
            fps = frames;
            frames = 0;
            lastFrame = tick;
        }
    }
    
    public int getFps(){
        return fps;
    }
    
    public long getTime(){
        return Sys.getTime();
    }
    
    public int getDelta(){
        return delta;
    }
}
