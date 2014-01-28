/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Entities;

import org.cakemix.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class Player extends Entity {

    // Sprite, position, velocity etc
    // All contained within entity
    // This is concerned with user input handleing
    // Used for holding keys
    // 0= up, 1 = down, 2 = left, 3 = right
    boolean[] move = new boolean[4];
    // used to denote mouse movement
    boolean mouseMove = true, moveByGrid = true;
    Vector2f mouseDestination;
    long lastInput = 0, inputGap = 75;
    int clampx, clampy;
    /*
     * Create a new Player @param location location of sprite to be loaded @param width Width of players frame (used for sprite and collision) @param height
     * Height of the players frame (used for sprite and collision)
     */

    public Player(String location, int width, int height) {
        super(location, width, height);
        //set mouse destination to invalid
        mouseDestination = new Vector2f(0, 0);
    }

    /*
     * Update the player
     */
    @Override
    public void update() {
        handleInput();

        super.update();
        if (vectorDistanceSquare(mouseDestination, position) < 16) {
            position.x = mouseDestination.x;
            position.y = mouseDestination.y;
        }
    }

    /*
     * Handle player input
     */
    protected void handleInput() {

        if (moveByGrid) {
            if (Game.timer.getTime() - lastInput > inputGap) {
                lastInput = Game.timer.getTime();
                handleKeyboardGrid();
            }
        } else {
            handleKeyboardDirect();
        }
        if (mouseMove) {
            handleMouse();
        } else {
            mouseDestination.x = 0;
            mouseDestination.y = 0;
        }
    }

    public void handleMouse() {

        if (Mouse.isButtonDown(0)) {
            mouseDestination.x = Mouse.getX() + Game.camera.getX();
            // need to do some jiggery pokery withthe y mouse co ord
            // becuase the co-ords are the reverse of each other
            mouseDestination.y = (Game.camera.getY() + Display.getHeight()) - Mouse.getY();
            if (moveByGrid) {
                clampx = clampToGrid(mouseDestination.x, getWidth());
                clampy = getHeight();
                mouseDestination.x = clampToGrid(mouseDestination.x, (int) (getWidth() / Game.scale));
                mouseDestination.y = clampToGrid(mouseDestination.y, (int) (getHeight() / Game.scale));
            }
        }

        if (mouseDestination.x != -1 && mouseDestination.y != -1) {
            Vector2f delta = new Vector2f();
            Vector2f.sub(mouseDestination, position, delta);
            double distance = vectorDistanceSquare(delta);

            if (distance > 16) {
                incrementVelocity(delta.x, delta.y);
            }
        }
    }

    public void handleKeyboardDirect() {
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
                    case 0: //up
                        delta.y = -5f * Game.timer.getDelta();
                        break;
                    case 1: //down
                        delta.y = 5f * Game.timer.getDelta();
                        break;
                    case 2: //left
                        delta.x = -5f * Game.timer.getDelta();
                        break;
                    case 3: //right
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
        mouseDestination.x = position.x;
        mouseDestination.y = position.y;
    }

    public void handleKeyboardGrid() {


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
        // increment destination value
        for (int x = 0; x < move.length; x++) {
            if (move[x]) {
                switch (x) {
                    case 0: //up
                        mouseDestination.y -= getHeight() / Game.scale;
                        break;
                    case 1: //down
                        mouseDestination.y += getHeight() / Game.scale;
                        break;
                    case 2: //left
                        mouseDestination.x -= getWidth() / Game.scale;
                        break;
                    case 3: //right
                        mouseDestination.x += getWidth() / Game.scale;
                        break;
                }
            }
        }
    }

    public static double vectorDistanceSquare(Vector2f left, Vector2f right) {
        Vector2f delta = new Vector2f();

        Vector2f.sub(left, right, delta);

        return (delta.x * delta.x) + (delta.y * delta.y);


    }

    public static double vectorDistanceSquare(Vector2f delta) {
        return (delta.x * delta.x) + (delta.y * delta.y);
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setX(int x) {
        position.x = x;
    }

    public void setY(int y) {
        position.y = y;
    }

    public void setDestination(float x, float y) {
        mouseDestination.x = x;
        mouseDestination.y = y;
    }

    public Vector2f getDestination() {
        return mouseDestination;
    }

    @Override
    public void draw() {
        super.draw();

        if (mouseDestination.x != position.x ||
                mouseDestination.y != position.y) {
            drawMousePoint();
        }
//        drawDebug();
    }

    public void drawDebug() {

        Vector2f delta = new Vector2f();
        Vector2f.sub(position, mouseDestination, delta);
        double distance = (delta.x * delta.x) + (delta.y * delta.y);
        Game.font.drawString("x " + position.x + " :y " + position.y + '\n'
                + "mx " + mouseDestination.x + " :my " + mouseDestination.y + '\n'
                + "cx " + clampx + " :cy " + clampy + '\n'
                + "vx " + velocity.x + " :vy " + velocity.y + '\n'
                + "dmx " + delta.x + " :dmy " + delta.y + '\n'
                + "distance " + (int) distance + '\n'
                + "Bounds x" + (position.x * Game.scale) + (getWidth()) + ":" + ((position.x * Game.scale) + (getWidth()) > Display.getWidth()) + '\n'
                + "Bounds y" + (position.y * Game.scale) + (getHeight()) + ":" + ((position.y * Game.scale) + (getHeight()) > Display.getHeight()),
                0 + Game.camera.getX(), 128);

    }

    private void drawMousePoint() {
        // Store the Curent model Matrix
        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        // enable point drawing and blending
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        //set point size
        GL11.glPointSize(2.0f);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBegin(GL11.GL_POINTS);

        GL11.glColor4f(0f, 0f, 0f, 1f);
        GL11.glVertex2f(mouseDestination.x * Game.scale, mouseDestination.y * Game.scale);

        GL11.glEnd();

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        float[] color = new float[]{1f, 0, 0, 1f};
        sprite.draw((int) mouseDestination.x, (int) mouseDestination.y, color);
    }
}
