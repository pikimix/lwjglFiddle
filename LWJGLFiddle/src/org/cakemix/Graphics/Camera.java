/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics;

import org.cakemix.Game;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class Camera {

    int x, y;
    Vector2f maxCameraPosition;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
        maxCameraPosition = new Vector2f(1000, 1000);
    }

    public void setLimits(int x, int y) {
        maxCameraPosition.x = x;
        maxCameraPosition.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void ScrollCamera(Vector2f position) {
        float ViewMargin = 0.5f, ViewMarginV = 0.5f;

        // Calculate the edges of the screen.
        float marginWidth = Display.getWidth() * ViewMargin;
        float marginHeight = Display.getHeight() * ViewMarginV;
        float marginLeft = Game.camera.getX() + marginWidth;
        float marginRight = Game.camera.getX() + Display.getWidth() - marginWidth;
        float marginTop = Game.camera.getY() + marginHeight;
        float marginBotom = Game.camera.getY() + Display.getHeight() - marginHeight;
        
        // Calculate how far to scroll when the focal point is near the edges of the screen.
        Vector2f cameraMovement = new Vector2f(0, 0);

        // check if need to move left or right
        // then assign a value based on distance from side
        if (position.x < marginLeft) {
            cameraMovement.x = position.x - marginLeft;
        } else if (position.x > marginRight) {
            cameraMovement.x = position.x - marginRight;
        }
        if (position.y < marginTop) {
            cameraMovement.y = position.y - marginTop;
        } else if (position.y > marginBotom) {
            cameraMovement.y = position.y - marginBotom;
        }

        // Update the camera position, but prevent scrolling off the ends of the map.
        // ie, the max camera position - the display size
        x = (int) clamp(x + cameraMovement.x, 0.0f, maxCameraPosition.x - Display.getWidth());
        y = (int) clamp(y + cameraMovement.y, 0.0f, maxCameraPosition.y - Display.getHeight());
       // System.out.println(x + ":" + y + "        " + position.x + ":" + position.y);
    }

    public float clamp(float val, float min, float max) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            if (max > min) {
                return max;
            }else{
                return min;
            }
        }
        return val;
    }
}
