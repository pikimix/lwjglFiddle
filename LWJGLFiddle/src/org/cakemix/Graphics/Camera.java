/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Graphics;

/**
 *
 * @author cakemix
 */
public class Camera {
    
    int x, y;
    
    public Camera(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void update(int x, int y){
        this.x = x;
        if (this.x <0){
            this.x = 0;
        }
        this.y = y;
        if (this.y < 0){
            y = 0;
        }
    }
    
    public void shift(int dx, int dy){
        x += dx;
        y += dy;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
