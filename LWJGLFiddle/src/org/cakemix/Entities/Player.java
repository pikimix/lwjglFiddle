/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Entities;

import org.cakemix.Graphics.ParticleEngine.Emitter;
import org.cakemix.Timer;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author cakemix
 */
public class Player extends Entity {

    // Sprite., position, velocity etc
    // All contained within entity
    // This is concerned with user input handleing
    // Used for holding keys
    // 0= up, 1 = down, 2 = left, 3 = right
    boolean[] move = new boolean[4];
    // used for engine trails and such (okasy, this is testing, but hey
    Emitter emitter;

    /*
     * Create a new Player @param location location of sprite to be loaded
     * @param width Width of players frame (used for sprite and collision)
     * @param height Height of the players frame (used for sprite and collision)
     */
    public Player(String location, int width, int height) {
        super(location, width, height);
        emitter = new Emitter(200, 0, 0, 100);
    }

    /*
     * Update the player
     */
    @Override
    public void update(Timer timer) {
        handleInput(timer);
        emitter.updatePosition(position);
        emitter.update(timer);
        
        super.update(timer);
    }

    /*
     * Handle player input
     */
    protected void handleInput(Timer timer) {

        // Get keyboard input
        while (Keyboard.next()) {
            // Y movement (negative = up, posative = down)
            if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                if (Keyboard.getEventKeyState()) {
                    move[0] = true;
                    move[1] = false;
                } else {
                    move[0] = false;
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    if (Keyboard.getEventKeyState()) {
                        move[1] = true;
                        move[0] = false;
                    } else {
                        move[1] = false;
                    }

                }
            }
            // X movement (negative = left, posative = right)
            if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                if (Keyboard.getEventKeyState()) {
                    move[2] = true;
                    move[3] = false;
                } else {
                    move[2] = false;
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    if (Keyboard.getEventKeyState()) {
                        move[3] = true;
                        move[2] = false;
                    } else {
                        move[3] = false;
                    }
                }
            }
        }
        // set up the incremental values
        float deltaX = 0,
                deltaY = 0;
        for (int x = 0; x < move.length; x++) {
            if (move[x]) {
                switch (x) {
                    case 0:
                        deltaY = -.05f * timer.getDelta();
                        break;
                    case 1:
                        deltaY = .05f * timer.getDelta();
                        break;
                    case 2:
                        deltaX = -.05f * timer.getDelta();
                        break;
                    case 3:
                        deltaX = .05f * timer.getDelta();
                        break;
                }
            }
        }
        // Finally, update the velocity
        incrementVelocity(deltaX, deltaY);
    }
    
    @Override
    public void draw(){
        super.draw();
        emitter.draw();
    }
}
