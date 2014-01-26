/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix;

import java.io.File;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;

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
