package org.cakemix;

import org.cakemix.Entities.Player;
import java.io.File;
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
            //set the display mode (change to vars later on)
            Display.setDisplayMode(new DisplayMode(800,600));
            // create window with above params
            Display.create();
        } catch (LWJGLException e){
            // print out any errors
            e.printStackTrace();
            // exit
            System.exit(1);
        }
        
        // Initialise everything that needs it
        init();
                    
        // while close is not requested
        // run logic/ render loops
        isRunning = true;
        // set up test sprite
        testy = new Player("img/ship.png", 64, 64);
        while ( !Display.isCloseRequested()){
            
            update();
            
            render();
        }
        
        //Destroy the display once your finished
        // send it to the falmes!
        Display.destroy();
        
        System.exit(0);
    }
    
    /**
     * Initialise anything that needs it
     * Mainly for opengl at the moment
     */
    public void init(){
        //initialise the timer
        timer = new Timer();
        
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
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
        
        // render test sprite
        testy.draw();
        
        //update the display
        Display.update();
        
        // Limit rendering to 60 fps
        Display.sync(60);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // load lwjgl native librarys
        System.setProperty("org.lwjgl.librarypath", 
                new File(new File(System.getProperty("user.dir"), "native"), 
                LWJGLUtil.getPlatformName()).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", 
                System.getProperty("org.lwjgl.librarypath"));
        new Game().start();
      
    }
}
