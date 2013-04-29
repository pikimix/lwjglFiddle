/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics.ParticleEngine;

import org.cakemix.Timer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author cakemix
 */
public class Particle {

    //Particles Position and Velocity (Similar to entity...)
    protected Vector2f position,
            velocity;
    // Particle colour
    // as with openGL rgba
    protected Vector4f colour;
    // How Long the particle lives
    protected int timeToLive;
    // Time the particle was created
    protected long timeCreated;

    /*
     * Create a new Particle
     * @param textureID the id of the texture to be rendered, held in emitter
     * @param ctime Current Time (becomes createdTime)
     */
    public Particle(float x, float y, float r, float g, float b, float a,
            long cTime, int ttl) {
        position = new Vector2f(x, y);
        colour = new Vector4f(r, g, b, a);
        timeToLive = ttl;
        timeCreated = cTime;
        java.util.Random rand = new java.util.Random();
        
        velocity = new Vector2f((rand.nextFloat() - .5f) / 4,
                rand.nextFloat()/3);
        velocity.normalise();
        velocity.scale(rand.nextFloat()/3);
    }

    /*
     * May implement this here or in another class
     * Will use this to update position, alpha, colour
     * Pretty much everything :P
     */
    public void update(Timer timer) {
        timeToLive -= timer.getDelta();
        position.x += velocity.x * timer.getDelta();
        position.y += velocity.y * timer.getDelta();
    }

    /*
     * check if a particle has outlived its timeToLive
     * @return true if its outived, false if it still has time
     */
    protected boolean hasDied() {
        if (timeToLive <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Draw the PArticle at Position
     */
    public void draw() {
        //GL11.glColor4f( colour.x, colour.y, colour.z, colour.w );
        GL11.glColor4f(0.5f, 0.5f, 1.0f, 1f);
        GL11.glVertex2f(position.x, position.y);

    }
}
