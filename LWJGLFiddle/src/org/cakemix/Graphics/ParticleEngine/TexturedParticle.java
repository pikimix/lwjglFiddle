/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics.ParticleEngine;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author cakemix
 */
public class TexturedParticle extends PointParticle {

    // super holds the gubbins
    // velocity, position, colour tint, time to live etc
    // this is concerned with the new textured part

    // This class will share some similaritys to a sprite
    // it basicly is one, but with a decay

    // texture ID of the particle
    protected int textureID;

    // the size of the particle
    protected int scale = 4;
    public TexturedParticle(int textureID, float x, float y, int s,
            float r, float g, float b, float a,
            long cTime, int ttl){
        super(x, y, r, g, b, a, cTime, ttl);
        this.textureID = textureID;
        scale = s;
    }

    @Override
    public void draw(){
        // Store the Curent model Matrix
        GL11.glPushMatrix();

        //enable textures (just incase)
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // Bind to the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);


        // Move to correct location
        GL11.glTranslatef(position.x - (scale/2),
                position.y - (scale/2),
                0);
        GL11.glColor3f(1, 1, 1);

        // Draw textured Quad to match the sprite
        GL11.glBegin(GL11.GL_TRIANGLES);
        {
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2i(0, 0);
            GL11.glTexCoord2d(0, 1f);
            GL11.glVertex2i(0, scale);
            GL11.glTexCoord2d(1f, 1f);
            GL11.glVertex2i(scale, scale);

            GL11.glTexCoord2d(1f, 1f);
            GL11.glVertex2i(scale, scale);
            GL11.glTexCoord2d(1f, 0);
            GL11.glVertex2i(scale, 0);
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2i(0, 0);

        }
        GL11.glEnd();

        GL11.glPopMatrix();
    }


}
