package org.cakemix;

import org.cakemix.Chat.ChatInput;
import org.cakemix.Graphics.Camera;
import org.cakemix.Graphics.SpriteFont;
import org.cakemix.world.Map;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class Game {

    // global level vars
    protected boolean isRunning = false;
    public static SpriteFont font;
    // width / height of window
    protected int width = 800, height = 600;
    // setup timer
    public static Timer timer;
    // world map
    protected Map map;
    public static float scale = 1f;
    public static Camera camera = new Camera(0, 0);
    public ChatInput chat;

    /**
     * Start the game Create the window, call init() then run the game loop
     * until either the window dies or isRunning is changed to false
     */
    public void start() {
        // catch any errors
        try {


            //set the display mode
            Display.setDisplayMode(new DisplayMode(width, height));

            //Set the display to be resizeable
            Display.setResizable(true);

            // create window with above params
            Display.create();
        } catch (LWJGLException e) {
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

        map = new Map();



        font = new SpriteFont("img/font.png");

        chat = new ChatInput();
        
        while (!Display.isCloseRequested() && isRunning) {
            if (Display.wasResized()) {
                width = Display.getWidth();
                height = Display.getHeight();
                initGL();
                GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

                //scale = width / 800f;
            }

            update();

            render();
        }

        //Destroy the display once your finished
        Display.destroy();

        // Exit the program with exit code 1
        // Success!
        System.exit(1);
    }

    /**
     * Initialise anything that needs it Mainly for opengl at the moment
     */
    public void init() {
        //initialise the timer
        timer = new Timer();

        initGL();
    }

    protected void initGL() {
        // Initialise opengl
        // Before we can do anything, we need to init opengl

        // let it know to expect 2d textures
        // dont want to surprise the poor thing
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // And tell it to not bother with depth test
        // Dont want to waste its time with 3d gumph
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // enable alpha blending
        // for sweet, sweet transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // change background colour
        GL11.glClearColor(1, 1, 1, 1);

        // not sure bout these, but they make it work
        // Look into this one later...
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        // seams to work best as is, commented values dont resize the screen
        GL11.glOrtho(
                0,//camera.getX(),
                Display.getWidth(),//camera.getX()+Display.getDisplayMode().getWidth(),
                Display.getHeight(),//camera.getY()+Display.getDisplayMode().getHeight(),
                0,//camera.getY(), 
                -1,
                1);
    }

    /**
     * Update game loop For user input and shit
     */
    public void update() {
        // update game timer
        timer.update();
        map.update();
        chat.update();
    }

    /**
     * Render loop For drawing and shit
     */
    public void render() {
        // clear the screen ready for next frame
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // move the camera
        GL11.glTranslatef(-camera.getX(), -camera.getY(), 0);

        map.draw();

        chat.draw(0, 0);
        //update the display
        Display.update();

        // Limit rendering to 60 fps
        Display.sync(60);
    }
}
