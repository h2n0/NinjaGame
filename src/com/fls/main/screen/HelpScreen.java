package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import fls.engine.main.art.Art;

public class HelpScreen extends Screen {

    private int delay = 0;
    private int selected = 0;
    private Screen parent;
    private String[][] messages = new String[][] {
            {
                    "The help screen",
                    "",
                    "Here you can find all of the",
                    "information about this game",
                    "Including how to play and how to use the level editor",
                    "Press the left and right arrow keys to navigate"
    },
            {
                    "How to play",
                    "",
                    "Left - to go left",
                    "Right - to go right",
                    "Up - climb up ladders",
                    "Down - climb down ladders",
                    "Space - jump"
    },
            {
                    "The Level Editor",
                    "",
                    "To create your own levels you just need to click",
                    "First select the square the you want to change and",
                    "left click this will chnage the tile",
                    "",
                    "Now you need to add a spawn point for the player",
                    "if you right click in the square you want the player to spawn in",
                    "That square will now display a very low res image of the player",
                    "That is the spawn point of the player.",
                    "",
                    "Now yo are ready to play your level if you now press enter",
                    "You will be playing the level that you just created",
                    "so after you have played around in your level you can go back to",
                    "the level editor screen by pressing ESC",
                    "from here you can continue to make levels and if you are happy",
                    "with how the level has turned out you can save it by pushing shift."
    },
            {
                    "Taking screenshots",
                    "",
                    "Just to save you effort I have built a system that will take a screenshot for you",
                    "Just press '0' when playing the game and it will create a screeshot for you",
                    "it's that simple."
    }
    };

    public HelpScreen(Screen parent) {
        this.parent = parent;
    }

    public void render(Graphics g) {
        Art.fillScreen(ninja, g, Color.black);
        Art.drawScaledText(messages[selected][0], g, 10, 10, 3);
        for (int y = 1; y < messages[selected].length; y++) {
            Art.drawString(messages[selected][y], g, 10, 30 + (y * 8));
        }
        Art.drawString("Press ESCAPE to go back", g, 10, 345);
    }

    public void tick() {
        if (delay > 0) delay--;
        if (input.keys[KeyEvent.VK_ESCAPE]) setScreen(parent);
        if (input.keys[KeyEvent.VK_LEFT] && delay == 0) {
            if (selected <= 0) {
                selected = messages.length - 1;
                delay = 20;
                return;
            } else {
                selected--;
                delay = 20;
            }
        }
        if (input.keys[KeyEvent.VK_RIGHT] && delay == 0) {
            if (selected >= messages.length - 1) {
                selected = 0;
                delay = 20;
                return;
            } else {
                selected++;
                delay = 20;
            }
        }
    }

}
