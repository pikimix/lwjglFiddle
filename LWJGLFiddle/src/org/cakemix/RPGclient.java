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

/**
 *
 * @author cakemix
 */
public class RPGclient {

    /**
     * @param args the command line arguments
     */
    private Game game;

    public RPGclient() {

        // create the frame to hold the window
        JFrame frame = new JFrame();

        JLabel lbl = new JLabel("Hello");

        JScrollPane panel = new JScrollPane(lbl,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setPreferredSize(new Dimension(128,128));
        panel.setMinimumSize(new Dimension(128, 128));

        // Create a new canvas and set its size.
        Canvas canvas = new Canvas();
        canvas.setSize(640, 480);
        // This is the magic!  The setParent method attaches the
        // opengl window to the awt canvas.
        try {
            Display.setParent(canvas);
        } catch (LWJGLException e) { // if it fails print out the error
            e.printStackTrace();
            System.exit(0);
        }

        // textbox
        JTextField chat = new JTextField();

        frame.add(panel, BorderLayout.WEST);
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(chat, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        final Game game = new Game();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                game.isRunning = false;
            }
        });

//        frame.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                pane.setSize(top.getRightComponent().getSize());
//                canvas.setSize(pane.getSize());
//            }
//        });

        game.start();

    }

    public static void main(String[] args) {
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
