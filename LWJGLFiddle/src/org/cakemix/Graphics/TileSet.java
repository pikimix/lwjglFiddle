/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics;

import java.awt.image.BufferedImage;
import java.util.Random;
import org.cakemix.Loaders.TextureLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.cakemix.Game;

/**
 *
 * @author cakemix
 */
public class TileSet {

    // Store the tileset graphic in memory
    protected int textureID;
    // set size (width, height) in pixels
    protected int width, height;
    // no of tiles wide by tiles high
    // default atlas, 24x16 grid
    protected int atlasWidth = 30,
            atlasHeight = 16;
    // We Also need to know the size of the tiles
    // Default is 64x64
    protected int tileWidth = 16, tileHeight = 16;

    public TileSet(String location) {
 
        BufferedImage texture = TextureLoader.loadImage(this.getClass().getResourceAsStream('/' +location));
        //BufferedImage texture = TextureLoader.loadImage(System.getProperty("user.dir") + '/' + location);
        textureID = TextureLoader.loadTexture(texture);
        width = texture.getWidth();
        height = texture.getHeight();
        tileWidth = width / atlasWidth;
        tileHeight = height / atlasHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileWidth() {
        return (int) (tileWidth * Game.scale);
    }

    public int getTileHeight() {
        return (int) (tileHeight * Game.scale);
    }

    public float[] getTile() {
        float[] tile = {0f, 0f, 1f, 1f};

        int x = 0, y = 9;

        // currently set for grass
        // get the x value
        tile[0] = x * (tileWidth / (float) width);
        // Get the y value
        tile[1] = y * (tileHeight / (float) height);
        // get the x + u value
        tile[2] = (x * (tileWidth / (float) width))
                + (tileWidth / (float) width);
        // get the y + v value
        tile[3] = (y * (tileHeight / (float) height))
                + (tileHeight / (float) height);

        return tile;
    }

    public float[] getRandom() {
        float[] tile = {0f, 0f, 1f, 1f};

        Random r = new Random();

        int x = r.nextInt(atlasWidth +1), y = r.nextInt(atlasHeight + 1);

        // currently set for top left only
        // get the x value
        tile[0] = x * (tileWidth / (float) width);
        // Get the y value
        tile[1] = y * (tileHeight / (float) height);
        // get the x + u value
        tile[2] = (x * (tileWidth / (float) width))
                + (tileWidth / (float) width);
        // get the y + v value
        tile[3] = (y * (tileHeight / (float) height))
                + (tileHeight / (float) height);

        return tile;
    }

    public void draw(float x, float y) {
        // get the current tile
        float[] tile = getTile();

        // Store the Curent model Matrix
        // ie. All the quads I've drawn
        GL11.glPushMatrix();

        //enable textures (just incase)
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // Bind to the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // Move to correct location
        GL11.glTranslatef(x * getTileWidth(), y * getTileHeight(), 0);
        // set color (tint and alpha)
        // need to get a variable control on the alpha
        // oooo, swanky transparency effects xD
        GL11.glColor4f(1, 1, 1, 1);

        // Draw textured Quad to match the sprite
        GL11.glBegin(GL11.GL_TRIANGLES);
        {
            // top left
            GL11.glTexCoord2d(tile[0], tile[1]);
            GL11.glVertex2i(0, 0);
            // bottom left
            GL11.glTexCoord2d(tile[0], tile[3]);
            GL11.glVertex2i(0, (int) (tileHeight * Game.scale));
            // bottom right
            GL11.glTexCoord2d(tile[2], tile[3]);
            GL11.glVertex2i((int) (tileWidth * Game.scale), (int) (tileHeight * Game.scale));

            // bottom right
            GL11.glTexCoord2d(tile[2], tile[3]);
            GL11.glVertex2i((int) (tileWidth * Game.scale), (int) (tileHeight * Game.scale));
            // top right
            GL11.glTexCoord2d(tile[2], tile[1]);
            GL11.glVertex2i((int) (tileWidth * Game.scale), 0);
            // top left
            GL11.glTexCoord2d(tile[0], tile[1]);
            GL11.glVertex2i(0, 0);
        }
        // done doin shit and stuff
        GL11.glEnd();

        // not sure what this does
        // but things break without it
        GL11.glPopMatrix();
    }
}
