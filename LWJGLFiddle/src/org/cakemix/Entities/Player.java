/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Entities;

import org.cakemix.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

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
    // used to denote mouse movement
    boolean mouseMove = true;
    Vector2f mouseDestination;

    /*
     * Create a new Player @param location location of sprite to be loaded @param width Width of players frame (used for sprite and collision) @param height
     * Height of the players frame (used for sprite and collision)
     */
    public Player(String location, int width, int height) {
        super(location, width, height);
        //set mouse destination to invalid
        mouseDestination = new Vector2f(-1, -1);
    }

    /*
     * Update the player
     */
    @Override
    public void update() {
        handleInput();
        screenCheck();

        super.update();
    }

    /*
     * Handle player input
     */
    protected void handleInput() {

        handleKeyboard();
        if (mouseMove) {
            handleMouse();
        } else {
            mouseDestination.x = -1;
            mouseDestination.y = -1;
        }
    }

    public void handleMouse() {

        if (Mouse.isButtonDown(0)) {
            mouseDestination.x = Mouse.getX() / Game.scale;
            mouseDestination.y = (Display.getHeight() / Game.scale) - (Mouse.getY() / Game.scale);
        }

        if (mouseDestination.x != -1 && mouseDestination.y != -1) {
            Vector2f delta = new Vector2f();

            Vector2f.sub(mouseDestination, position, delta);

            double distance = (delta.x * delta.x) + (delta.y * delta.y);

            if (distance > 16*16)
            incrementVelocity(delta.x, delta.y);
        }
    }

    public void handleKeyboard() {
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
        Vector2f delta = new Vector2f();
        for (int x = 0; x < move.length; x++) {
            if (move[x]) {
                switch (x) {
                    case 0:
                        delta.y = -5f * Game.timer.getDelta();
                        break;
                    case 1:
                        delta.y = 5f * Game.timer.getDelta();
                        break;
                    case 2:
                        delta.x = -5f * Game.timer.getDelta();
                        break;
                    case 3:
                        delta.x = 5f * Game.timer.getDelta();
                        break;
                }
            }
        }

        if (delta.x != 0 || delta.y != 0) {
            mouseMove = false;
        } else {
            mouseMove = true;
        }

        // Finally, update the velocity
        incrementVelocity(delta.x, delta.y);
    }

    /*
     * Keep the player on the screen
     */
    protected void screenCheck() {
        if (position.x < 0) {
            position.x = 0;
        } else {
            if ((position.x * Game.scale) + (sprite.getWidth() * Game.scale) > Display.getWidth()) {
                position.x = (int) (((Display.getWidth() / Game.scale) - sprite.getWidth()));
            }
        }
        if (position.y < 0) {
            position.y = 0;
        } else {
            if ((position.y * Game.scale) + (sprite.getHeight() * Game.scale) > Display.getHeight()) {
                position.y = (int) (((Display.getHeight() / Game.scale) - sprite.getHeight()));
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        Vector2f delta = new Vector2f();
        Vector2f.sub(position, mouseDestination, delta);
        double distance =(delta.x * delta.x) + (delta.y * delta.y);
        Game.font.drawString("x " + position.x + " :y " + position.y + '\n'
                + "vx " + velocity.x + " :vy " + velocity.y + '\n'
                + "mx " + mouseDestination.x + " :my " + mouseDestination.y + '\n'
                + "dmx " + delta.x + " :dmy " + delta.y + '\n'
                + "distance " + (int)distance + '\n'
                + "Bounds x" + (position.x * Game.scale) + (sprite.getWidth() * Game.scale) + ":" + ((position.x * Game.scale) + (sprite.getWidth() * Game.scale) > Display.getWidth()) + '\n'
                + "Bounds y" + (position.y * Game.scale) + (sprite.getHeight() * Game.scale) + ":" + ((position.y * Game.scale) + (sprite.getHeight() * Game.scale) > Display.getHeight()), 1, 64);

    }
}
