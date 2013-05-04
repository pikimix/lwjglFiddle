package org.cakemix;

import java.awt.Dimension;
import org.cakemix.Entities.*;
import java.io.File;
import org.cakemix.Graphics.ParticleEngine.Emitter;
import org.cakemix.Graphics.SpriteFont;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author cakemix
 */
public class Game {

    // global level vars
    protected boolean isRunning = false;
    public Dimension newDim;

    SpriteFont font;

    // width / height of window
    protected int width = 800, height = 600;

    // setup timer
    protected Timer timer;

    // hurum, test sprite
protected Player testy;
    /**
     * Start the game
     * Create the window, call init()
     * then run the game loop
     * until either the window dies
     * or isRunning is changed to false
     */
    public void start(){
        // catch any errors
        try {

            width = Display.getParent().getWidth();
            height = Display.getParent().getHeight();

            Display.create();
            //set the display mode
            Display.setDisplayMode(new DisplayMode(width,height));
            //Set the display to be resizeable
            Display.setResizable(true);
            // create window with above params
        } catch (LWJGLException e){
            // print out any errors
            e.printStackTrace();
            // exit
            System.exit(0);
        }

        // Initialise everything that needs it
        init();

        // while close is not requested
        // run logic/ render loops
        isRunning = true;
        // set up test sprite
        testy = new Player("img/ship.png", 64, 64);

        font = new SpriteFont("img/font.png");

        while ( /*!Display.isCloseRequested() && */ isRunning){
            if (Display.wasResized()){
                //try{
                width = Display.getWidth();
                height = Display.getHeight();
                //Display.setDisplayMode(new DisplayMode(newDim.width, newDim.height));
                initGL();
                //GL11.glViewport(0, 0, newDim.width, newDim.height);

                newDim = null;
                /*} catch (LWJGLException e){
                    e.printStackTrace();
                    System.exit(0);
                }*/
            }

            update();

            render();
        }

        //Destroy the display once your finished
        // send it to the falmes!
        Display.destroy();

        // Exit the program with exit code 1
        // Success!
        System.exit(1);
    }


    /**
     * Initialise anything that needs it
     * Mainly for opengl at the moment
     */
    public void init(){
        //initialise the timer
        timer = new Timer();

        initGL();
    }

    protected void initGL(){
        // Initialise opengl
        // Before we can do anything, we need to init opengl

        // let it know to expect 2d textures
        // dont want to surprise the poor thing
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // And tell it to not bother with depth test
        // Dont want to waste its time with 3d gumph
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // change background colour
        GL11.glClearColor(1,1,1,1);

        // not sure bout these, but they make it work
        // Look into this one later...
        GL11.glMatrixMode(GL11.GL_PROJECTION);
	GL11.glLoadIdentity();

	GL11.glOrtho(0, width, height, 0, -1, 1);
    }

    /**
     * Update game loop
     * For user input and shit
     */
    public void update(){
        // update game timer
        timer.update();

        testy.update(timer);

    }

    /**
     * Render loop
     * For drawing and shit
     */
    public void render(){
        // clear the screen ready for next frame
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // render test sprite
        testy.draw();

        font.drawString("Width = " + Integer.toString(Display.getWidth()),0, 0);
        font.drawString("Height= " + Integer.toString(Display.getHeight()),0, 32);

        //update the display
        Display.update();
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        // Limit rendering to 60 fps
        Display.sync(60);
    }

}
