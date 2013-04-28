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
public class Emitter {

    // Store the particles
    protected Particle[] particle;
    // Max new particles per tick
    protected int genRate = 100;
    // default Time to live
    protected int timeToLive = 250;
    // Emitter position
    protected Vector2f position;
    // If set, particles are generated psudo randomly around 
    // emitter position, based on jitterRadius
    protected boolean jitter = true;
    protected float jitterRadius = 5;
    // Default colour for created particles
    protected Vector4f colour = new Vector4f(1, 1, 1, 1);
    // turn it on or off
    // usefull so it doesnt do its shit when it cant be seen
    protected boolean emiting = false;

    /*
     * Create a new emitter @param numPart Number of particles (max)
     */
    public Emitter(int numPart, int x, int y, int ttl) {
        particle = new Particle[numPart];
        position = new Vector2f(x, y);
        timeToLive = ttl;
    }

    // create new particles
    protected void generate(Timer timer) {
        int created = 0;
        
        
        for (int i = 0; i < particle.length; i++) {
            if (particle[i] == null) {
                particle[i] = new Particle(position.x, position.y,
                        colour.x, colour.y, colour.z, colour.w,
                        timer.getTime(), timeToLive);
                created++;
                if (created > genRate) {
                    break;
                }
            }
        }
    }

    /*
     * Remove dead particles
     */
    protected void cull(Timer timer) {
        for (int i = 0; i < particle.length; i++) {
            if (particle[i] != null) {
                if (particle[i].hasDied(timer.getTime())) {
                    particle[i] = null;
                }
            }
        }
    }

    public void update(Timer timer) {
        cull(timer);
        generate(timer);
    }

    public void updatePosition(Vector2f position) {
        this.position = position;
    }

    public void draw() {
        // Store the Curent model Matrix
        //GL11.glPushMatrix();

        // enable point drawingand blending
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        //set point size
        GL11.glPointSize(10.0f);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBegin(GL11.GL_POINTS);


        for (int i = 0; i < particle.length; i++) {
            if (particle[i] != null) {
                particle[i].draw();
                
            }
        }

        GL11.glEnd();
        
    }
}
