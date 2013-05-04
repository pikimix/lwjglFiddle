/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics;

import org.cakemix.Timer;
import org.lwjgl.opengl.GL11;

/* 
 * Basic animated sprite
 * 4 polar directions, single animation type
 * Expand this later, either in class or as Xy extends AnimatedSprite
 * not sure yet
 */
public class AnimatedSprite extends Sprite {
    
    // no of frames wide by frames high
    // default atlas, 4x4 grid
    protected int atlasWidth = 4, 
                  atlasHeight = 4;
    
    // current frame
    // co-ordinate index in atlas
    // getFrame() converts this to usable values
    protected int[] currentFrame = { 0, 0 };
    
    // We Also need to know the size of the frames
    // Default is 64x64
    protected int frameWidth = 64, frameHeight = 64;
    
    // Last time the animation frame changed, and
    // delay between frames 
    // default to 4 fps (250 ms between changes)
    protected long lastFrame = 0,
                    frameDelay = 250;
    
    public AnimatedSprite(String location, int width, int height) {
        
        super(location);
        frameWidth = width;
        frameHeight = height;
    }
    
    @Override
    public int getWidth(){
        return frameWidth;
    }
    
    @Override
    public int getHeight(){
        return frameHeight;
    }
    
    /*
     * Setup new atlas
     * used for non basic sheets
     */
    public void setAtlas(int newWidth, int newHeight){
        //set he atlas to the new values
        atlasWidth = newWidth;
        atlasHeight = newHeight;
    }
    
    /*
     * return arary of floats that are the current frame
     * [0] = x
     * [1] = y
     * [2] = x + u
     * [3] = y + v
     * 
     * NOTE:: This function only holds true so long as
     * the atlas and frame sizes are the correct size
     */
    public float[] getFrame(){
        // default to full texture
        float[] frame = {0f,0f,1f,1f};
        
        // DONT FORGET THE CASTS!!!
        // Will do integer division otherwise
        // ie, it will = 0!
        
        // get the x value
        frame[0] = currentFrame[0] * ( frameWidth/(float)width );
        // Get the y value
        frame[1] = currentFrame[1] * ( frameHeight/(float)height );
        // get the x + u value
        frame[2] = (currentFrame[0] *  ( frameWidth/(float)width) ) +
                    ( frameWidth/(float)width ) ;
        // get the y + v value
        frame[3] = (currentFrame[1] * ( frameHeight/(float)height ) ) +
                    ( frameHeight/(float)height );
        // return the frame value
        return frame;
    }
    
    @Override
    public void update(Timer timer){
        
        if (timer.getTime() >= lastFrame + frameDelay){
            animate();
            lastFrame = timer.getTime();
        }
    }
    
    /*
     * update current animation frame
     */
    public void animate(){
        currentFrame[0]++;
        if (currentFrame[0] >= atlasWidth){
            currentFrame[0] = 0;
        }
    }
    
    /* Change direction (ie, texture y position)
     * If direction is outside of atlas, default to 0
     */
    public void changeDirection(int direction){
        currentFrame[1] = direction;
        if (currentFrame[1] >= atlasHeight){
            currentFrame[1] = 0;
        }
    }
    
    @Override
    /*
     * draw sprite at co-ords given
     */
    public void draw(float x, float y){
        // get the current frame
        float[] frame = getFrame();
        
        // Store the Curent model Matrix
        // ie. All the quads I've drawn
        GL11.glPushMatrix();
        
        // Bind to the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
       
        // Move to correct location
        GL11.glTranslatef(x, y, 0);
        // set color (tint and alpha)
        // need to get a variable control on the alpha
        // oooo, swanky transparency effects xD
        GL11.glColor4f(1,1, 1, 1);
        
        // Draw textured Quad to match the sprite
        GL11.glBegin(GL11.GL_QUADS);
        {
            // top left
            GL11.glTexCoord2d(frame[0], frame[1]);
            GL11.glVertex2i(0, 0);
            
            // top right
            GL11.glTexCoord2d(frame[0], frame[3]);
	    GL11.glVertex2i(0, frameHeight);
	    
            // bottom left
            GL11.glTexCoord2d(frame[2], frame[3]);
	    GL11.glVertex2i(frameWidth,frameHeight);
	    
            // bottom right
            GL11.glTexCoord2d(frame[2], frame[1]);
	    GL11.glVertex2i(frameWidth,0);
        }
        // done doin shit and stuff
        GL11.glEnd();
        
        // not sure what this does
        // but things break without it
        GL11.glPopMatrix();
    }
}
