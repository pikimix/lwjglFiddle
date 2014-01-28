package org.cakemix.Entities;

import org.cakemix.Graphics.AnimatedSprite;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class Entity {

    // entities sprite
    protected AnimatedSprite sprite;
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
     * Update entity used to update entities position
     */
    public void update() {
        // update the sprite frame
        sprite.update();

        // apply physics before adding velocity to position
        applyPhysics();

        // update entitys position
        position.x += velocity.x;
        position.y += velocity.y;
    }
    
    public Vector2f getPosition(){
        return position;
    }
    public Vector2f getVelocity(){
        return velocity;
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
        velocity.x *= 0.75;
        velocity.y *= 0.75;

        // if the velocty is super low, make it 0
        // otherwise the entity will never stop
        if (Math.abs(velocity.x) < 0.5) {
            velocity.x = 0;
        }
        if (Math.abs(velocity.y) < 0.5) {
            velocity.y = 0;
        }
        // finally make sure your not exceeding max velocty
        // yay for vectors!
        if (velocity.length() > mvX) {
            velocity.normalise();
            velocity.scale(mvX);
        }

        // update sprites direction
        if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
            if (velocity.x != 0) {
                if (velocity.x > 0) {
                    sprite.changeDirection(Direction.EAST);
                } else {
                    sprite.changeDirection(Direction.WEST);
                }
            } else {
                sprite.changeDirection(Direction.SOUTH);
            }
        } else {
            if (velocity.y != 0) {
                if (velocity.y > 0) {
                    sprite.changeDirection(Direction.SOUTH);
                } else {
                    sprite.changeDirection(Direction.NORTH);
                }
            } else {
                sprite.changeDirection(Direction.SOUTH);
            }
        }
    }

    /*
     * return the value given as a grid co-ordinate @param value Absolute co-ordinate Value @param size Grid size @return value clamped to grid size
     */
    public int clampToGrid(float value, int size) {
        //clamp to given grid
        return (int) (value / size) * size;
    }
    
    public int getWidth(){
        return sprite.getWidth();
    }
    public int getHeight(){
        return sprite.getHeight();
    }

    /*
     * Render sprite at {x, y}
     */
    public void draw() {

        sprite.draw((int)position.x, (int)position.y);
//        sprite.draw(clampToGrid(position.x,sprite.getWidth()), clampToGrid(position.y,sprite.getHeight()));
    }

}