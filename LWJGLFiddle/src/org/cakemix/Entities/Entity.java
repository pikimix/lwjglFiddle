package org.cakemix.Entities;

import org.cakemix.Graphics.AnimatedSprite;
import org.cakemix.Graphics.Sprite;
import org.cakemix.Timer;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class Entity {

    // entities sprite
    private Sprite sprite;
    // Entities current velocity and position
    protected Vector2f velocity, position;
    // entities max velocity
    protected float mvX = 5,
            mvY = 5;

    public Entity(String location, int width, int height) {
        // setup the velocity and position of the entity
        // Also set them both to 0,0
        position = new Vector2f(0, 0);
        velocity = new Vector2f(0, 0);

        // Create a sprite for the entity
        sprite = new AnimatedSprite(location, width, height);

    }

    /*
     *
     * Update entity Timer timer used for sprite animation delta{x, y} used to
     * update entities position
     */
    public void update(Timer timer) {
        // update the sprite frame
        sprite.update(timer);

        // apply physics before adding velocity to position
        applyPhysics();

        // update entitys position
        position.x += velocity.x;
        position.y += velocity.y;
    }

    /*
     * Increment velocity by ammounts stated
     */
    protected void incrementVelocity(float x, float y) {
        velocity.x += x;
        velocity.y += y;
    }

    /*
     * set velocity to ammounts stated
     */
    protected void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    /*
     * Okay, take this loosely Currently, it applies a pseudo drag effect
     */
    protected void applyPhysics() {
        // apply drag to both x && y
        velocity.x *= 0.95;
        velocity.y *= 0.95;

        // if the velocty is super low, make it 0
        // otherwise the entity will never stop
        if (Math.abs(velocity.x) < 0.1) {
            velocity.x = 0;
        }
        if (Math.abs(velocity.y) < 0.1) {
            velocity.y = 0;
        }
        // finally make sure your not exceeding max velocty
        // yay for vectors!
        if (velocity.length() > mvX) {
            velocity.normalise();
            velocity.scale(mvX);
        }
    }

    /*
     *
     * Render sprite at {x, y}
     */
    public void draw() {
        sprite.draw(position.x, position.y);
    }
}