package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.fls.main.art.Sprites;
import com.fls.main.core.Stats;

import fls.engine.main.art.Art;

public class MessageScreen extends Screen {

    private GameScreen parent;
    private int delay = 0;
    private int id;

    private String[][] signs = new String[][] {
            {
                    "Hello there.",
                    "",
                    "Well this is a thing that i have been working on",
                    "For a while now and you seem to have gotten a hold",
                    "of a copy, well since you have a copy you my aswell",
                    "try it out",
                    "",
                    "Have fun."
    },
            {
                    "Reading signs.",
                    "",
                    "This is the last sign that you are forced to read",
                    "but if you want to read them again then stand in",
                    "front of them and press up."
    },
            {
                    "The ups and downs of ladders.",
                    "",
                    "If you are on a ladder and push up you will be",
                    "climb to ladder, however, if you push down",
                    "you will slide down the ladder."
    },
            {
                    "Well they have a point.",
                    "",
                    "These are spikes if you touch them you",
                    "will die a painful death simple."
    },
            {
                    "Convayor belts",
                    "",
                    "I see that you have already tried them out",
                    "as you can see they will attempt to push you",
                    "around."
    },
            {
                    "Well thats it",
                    "",
                    "Thanks for playing this if you want to know some",
                    "things like: how much you died or how many times",
                    "you jumped then you can read the next sign.",
    },
            {
                    "Stats:",
                    "yep this part is all about you",
                    "",
                    "Time Taken: " + Stats.instance.getTimeString(),
                    "Deaths: " + Stats.instance.deaths,
                    "Jumps: " + Stats.instance.jumps
    }
    };

    public MessageScreen(GameScreen screen, int id) {
        this.parent = screen;
        this.id = id;
        delay = 30;
        Art.setTextCol(Color.white);
    }

    public void render(Graphics g) {
        parent.render(g);
        int xs = 0;
        int ys = signs[id].length + 3;
        for (int y = 0; y < signs[id].length; y++) {
            int s = signs[id][y].length();
            if (s > xs) xs = s;
        }
        int xp = 260 - xs * 3;
        int yp = 120 - ys * 3;
        for (int x = 0 - 1; x < xs + 1; x++) {
            for (int y = 0 - 1; y < ys + 1; y++) {
                int xf = 1;
                int yf = 2;
                if (x < 0) xf--;
                if (y < 0) yf--;
                if (x >= xs) xf++;
                if (y >= ys) yf++;
                g.drawImage(Sprites.parts[xf][yf], xp + x * 6, yp + y * 6, null);
            }
        }
        for (int y = 0; y < signs[id].length; y++) {
            Art.drawString(signs[id][y], g, xp, yp + y * 6);
            if (delay == 0) Art.drawString("Press Enter to exit", g, xp, yp + signs[id].length * 6 + 6);
        }
    }

    public void tick() {
        if (delay > 0) delay--;
        if (input.keys[KeyEvent.VK_ENTER] && delay == 0) {
            setScreen(parent);
        }
    }

}
