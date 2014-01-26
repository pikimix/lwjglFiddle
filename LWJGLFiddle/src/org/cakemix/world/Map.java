/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.world;

import java.awt.Font;
import java.util.Random;
import org.cakemix.Entities.Player;
import org.cakemix.Game;
import org.cakemix.Graphics.TileSet;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

/**
 *
 * @author cakemix
 */
public class Map {

    protected Player localPlayer;
    // test tile set
    protected TileSet tiles;
    protected int[][] background = new int[30][16];
    int tx = 0, ty = 0;

    public Map() {
        localPlayer = new Player("img/debug2x4.png", 2, 4);
        //setup test tileset
        tiles = new TileSet("img/tileset.png");
        randomBackground();
    }

    private void randomBackground() {
        int t = 0;
            for (int y = 0; y < 16; y++) {
        for (int x = 0; x < 30; x++) {
                background[x][y] = t;
                t++;
                System.out.print(background[x][y] + ":");
            }
            
        System.out.println();
        }
    }

    public void update() {

        localPlayer.update();
        
    }

    public void draw() {
        fillTiles();
        localPlayer.draw();
        drawGrid();
    }

    protected void fillTiles() {
            for (int y = 0; y  < 16 /* tiles.getTileHeight() < Display.getHeight()*/; y++) {
        for (int x = 0; x < 30 /* tiles.getTileWidth() < Display.getWidth()*/; x++) {
                tiles.draw(x, y,background[x][y]);
            }
        }
    }

    private void drawGrid() {
        int x = 0, y = 0;
        while (x < Display.getWidth()) {
            drawLine(new Point(x, 0), new Point(x, Display.getHeight()));
            x += localPlayer.getWidth();
        }
        while (y < Display.getHeight()) {
            drawLine(new Point(0, y), new Point(Display.getWidth(), y));
            y += localPlayer.getHeight();
        }
    }

    private void drawLine(Point point, Point point2) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0f, 0f, 0f, 0.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2d(point.getX(), point.getY());
        GL11.glVertex2d(point2.getX(), point2.getY());
        GL11.glEnd();
    }
}
