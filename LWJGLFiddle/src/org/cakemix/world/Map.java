/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.world;

import java.util.Random;
import org.cakemix.Entities.Player;
import org.cakemix.Game;
import org.cakemix.Graphics.TileSet;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class Map {

    protected Player localPlayer;
    // test tile set
    protected TileSet tiles;
    protected int[][] background;
    int width = 30, height = 30;
    int tx = 0, ty = 0;

    public Map() {
        localPlayer = new Player("img/debug2x4.png", 2, 4);
        //setup test tileset
        background = new int[width][height];
        tiles = new TileSet("img/tileset.png");
        randomBackground();
        Game.camera.setLimits(width * tiles.getTileWidth(), height * tiles.getTileHeight());
    }
    /*
     * Keep the player on the screen
     */

    protected void boundsCheck() {
        if (localPlayer.getPosition().x < 0) {
            localPlayer.setX(0);
        } else {
            if (localPlayer.getPosition().x > (width - 1) * tiles.getTileWidth()) {
                localPlayer.setX((width - 1) * tiles.getTileWidth());
            }
        }
        if (localPlayer.getPosition().y < 0) {
            localPlayer.setY(0);
        } else {
            if (localPlayer.getPosition().y > (height - 1) * tiles.getTileHeight()) {
                localPlayer.setY((height - 1) * tiles.getTileHeight());
            }
        }

        Vector2f d = new Vector2f(localPlayer.getDestination().x, localPlayer.getDestination().y);
        if (d.x < 0) {
            d.x = 0;
        } else {
            if (d.x > (width - 1) * tiles.getTileWidth()) {
                d.x = (width - 1) * tiles.getTileWidth();
            }
        }
        if (d.y < 0) {
            d.y = 0;
        } else {
            if (d.y > (height - 1) * tiles.getTileHeight()) {
                d.y = (height - 1) * tiles.getTileHeight();
            }
        }

        localPlayer.setDestination(d.x, d.y);

    }

    private void randomBackground() {
        Random r = new Random();
        //int t = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                background[x][y] = r.nextInt(16 * 30); //t;
                //t++;
            }
        }
    }

    public void update() {

        localPlayer.update();
        if (Mouse.isButtonDown(1)) {
            Game.camera.ScrollCamera(
                    new Vector2f
                        (Display.getWidth() - Mouse.getX(), Mouse.getY()));
        }
        //Game.camera.ScrollCamera(new Vector2f(localPlayer.getPosition().x, localPlayer.getPosition().y));
        boundsCheck();
    }

    public void draw() {
        fillTiles();
        localPlayer.draw();
        drawGrid();
    }

    protected void fillTiles() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles.draw(x, y, background[x][y]);
            }
        }
    }

    private void drawGrid() {
        int x = 0, y = 0;
        while (x < width + 1) {
            drawLine(new Point(x * tiles.getTileWidth(), 0),
                    new Point(x * tiles.getTileWidth(), height * tiles.getTileHeight()));
            x++;
        }
        while (y < height + 1) {
            drawLine(new Point(0, y * tiles.getTileHeight()),
                    new Point(width * tiles.getTileWidth(), y * tiles.getTileHeight()));
            y++;
        }
    }

    private void drawLine(Point point, Point point2) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0f, 0f, 0f, 1f);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2d(point.getX(), point.getY());
        GL11.glVertex2d(point2.getX(), point2.getY());
        GL11.glEnd();
    }
}
