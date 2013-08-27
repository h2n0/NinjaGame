package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;

import com.fls.main.core.Stats;

import fls.engine.main.art.Art;
import fls.engine.main.input.Input;

public class WinScreen extends Screen {

    public WinScreen() {
        Art.setTextCol(Color.white);
        Stats.reset();
    }

    public void render(Graphics g) {
        Art.fillScreen(ninja, g, Color.gray);
        Art.drawScaledText("Thanks for playing", g, 12, 100, 2);
        Art.drawScaledText("Press enter to go to the main menu", g, 12, 114, 2);
        Art.drawScaledText("Time Spent here: " + Stats.instance.getTimeString(), g, 100, 200, 2);
    }

    public void tick(Input input) {
        Stats.instance.time++;
        if (input.enter.isPressed()) setScreen(new TitleScreen());
    }

}
