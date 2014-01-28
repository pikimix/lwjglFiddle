/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cakemix.Chat;

import org.cakemix.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author cakemix
 */
public class ChatInput {

    // current text
    String input;

    public ChatInput() {
        input = "";
    }

    public void update() {

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                input += Keyboard.getEventCharacter();
            }

        }

    }

    public void draw(int x, int y) {
        if (!"".equals(input)) {
            Game.font.drawString(input, x, y);
        }
    }
}
