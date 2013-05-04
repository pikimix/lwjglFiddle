/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author cakemix
 */
public class SpriteFont extends Sprite {

    int frameWidth = 0;
    int frameHeight = 0;

    public SpriteFont(String location) {
        super(location);
        frameWidth = width / 16;
        frameHeight = height / 16;
        System.out.println(frameWidth);
    }

    /**
     * Convert the characters to ASCII value
     *
     * @param character character
     * @return ASCII value
     */
    public static int charToAscii(final char character) {
        return (int) character;
    }

    /**
     * Convert the ASCII value to character
     *
     * @param ascii ascii value
     * @return character value
     */
    public static char asciiToChar(final int ascii) {
        return (char) ascii;
    }

    public float[] getFrame(int f) {
        // default to full texture
        float[] frame = {0f, 0f, 1f, 1f};
        int[] currentFrame = {0, 0};
        currentFrame[1] = (f / 16);
        currentFrame[0] = (f % 16);
        // get the x value
        frame[0] = currentFrame[0] * (frameWidth / (float) width);
        // Get the y value
        frame[1] = currentFrame[1] * (frameHeight / (float) height);
        // get the x + u value
        frame[2] = (currentFrame[0] * (frameWidth / (float) width))
                + (frameWidth / (float) width);
        // get the y + v value
        frame[3] = (currentFrame[1] * (frameHeight / (float) height))
                + (frameHeight / (float) height);
        return frame;
    }

    public void drawCharacter(char character, int x, int y) {
        float[] frame = getFrame(charToAscii(character));

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
        GL11.glColor4f(1, 1, 1, 1);

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
            GL11.glVertex2i(frameWidth, frameHeight);

            // bottom right
            GL11.glTexCoord2d(frame[2], frame[1]);
            GL11.glVertex2i(frameWidth, 0);
        }
        // done doin shit and stuff
        GL11.glEnd();

        // not sure what this does
        // but things break without it
        GL11.glPopMatrix();
    }

    public void drawString( String string, int x, int y ){
        for (int i = 0; i < string.length(); i++){
            drawCharacter(string.charAt(i), x + (frameWidth * i), y);
        }
    }

    public void drawString( int string, int x, int y ){
        drawString(Integer.toString(string), x, y);
    }
}
