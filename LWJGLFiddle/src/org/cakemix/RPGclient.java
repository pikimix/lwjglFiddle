/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author cakemix
 */
public class RPGclient {

    /**
     * @param args the command line arguments
     */

    public RPGclient() {


        final Game game = new Game();

        game.start();

    }

    public static void main(String[] args) throws LWJGLException {
        // load lwjgl native librarys
        System.setProperty("org.lwjgl.librarypath",
                new File(new File(System.getProperty("user.dir"), "native"),
                LWJGLUtil.getPlatformName()).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath",
                System.getProperty("org.lwjgl.librarypath"));

        // create an arg[] check to see if its requesting
        // client or server mode

        // run the client
        new RPGclient();
        
        
    }
}
