/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.world;

import org.cakemix.Entities.Player;
import org.cakemix.Game;
import org.cakemix.Graphics.TileSet;
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

    public Map() {
        localPlayer = new Player("img/debug3x4.png", 3, 4);
        //setup test tileset
        tiles = new TileSet("img/tileset.png");
    }

    public void update() {

        localPlayer.update();
    }

    public void draw() {
        fillTiles();
        localPlayer.draw();
    }

    protected void fillTiles() {
        for (int x = 0; x * tiles.getTileWidth() < Display.getWidth(); x++) {
            for (int y = 0; y * tiles.getTileHeight() < Display.getHeight(); y++) {
                tiles.draw(x, y);
            }
        }
    }

    private void drawLine(Point point, Point point2) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0.0f, 0f, 000f, 0.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2d(point.getX(), point.getY());
        GL11.glVertex2d(point2.getX(), point2.getY());
        GL11.glEnd();
    }
}
