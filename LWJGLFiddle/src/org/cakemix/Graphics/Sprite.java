package org.cakemix.Graphics;

import java.awt.image.BufferedImage;
import org.cakemix.Loaders.TextureLoader;
import org.cakemix.Timer;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author cakemix
 */
public class Sprite {

    // Store the sprite sheet
    //private BufferedImage texture;
    protected int textureID;
    // render size (width, height)
    protected int width, height;
    //sprites curent frame
    protected int frameIndex;

    public Sprite(String location) {
       
        BufferedImage texture = TextureLoader.loadImage(this.getClass().getResourceAsStream('/' +location));
        //BufferedImage texture = TextureLoader.loadImage(System.getProperty("user.dir") + '/' +location);
        width = texture.getWidth();
        height = texture.getHeight();
        textureID = TextureLoader.loadTexture(texture);
    }

    /*
     *
     * Nothing really needed in here
     */
    public void update() {
        // use this for animation code
        // in sub class
    }

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    /*
     * Render the Sprite
     */
    public void draw(int x, int y) {
        // Store the Curent model Matrix
        GL11.glPushMatrix();

        //enable textures (just incase)
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // Bind to the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);


        // Move to correct location
        GL11.glTranslatef(x, y, 0);
        GL11.glColor3f(1, 1, 1);

        // Draw textured Quad to match the sprite
        GL11.glBegin(GL11.GL_TRIANGLES);
        {
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2i(0, 0);
            GL11.glTexCoord2d(0, 1f);
            GL11.glVertex2i(0, height);
            GL11.glTexCoord2d(1f, 1f);
            GL11.glVertex2i(width, height);

            GL11.glTexCoord2d(1f, 1f);
            GL11.glVertex2i(width, height);
            GL11.glTexCoord2d(1f, 0);
            GL11.glVertex2i(width, 0);
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2i(0, 0);

        }
        GL11.glEnd();

        GL11.glPopMatrix();

    }
}
