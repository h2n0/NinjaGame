package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextField;

import fls.engine.main.art.Art;
import fls.engine.main.input.Input;

public class DebugScreen extends Screen {

    private GameScreen parent;
    private JTextField cmdbar;

    public DebugScreen(GameScreen screen) {
        this.parent = screen;
        cmdbar = new JTextField("Type commands here");
        cmdbar.setBounds(200,0,100,20);
        cmdbar.setVisible(true);
        parent.ninja.frame.add(cmdbar);
    }

    @Override
    public void render(Graphics g) {
        parent.render(g);
        int x = 200;
        int y = 50;
        g.setColor(Color.black);
        g.fillRect(0, 0, x, y);
        Art.setTextCol(0xffffff);
        Art.drawString("X:" + (int) parent.player.x, g, 0, 0);
        Art.drawString("Y:" + (int) parent.player.y, g, 0, 8);
        Art.drawString("onGround:" + parent.player.onGround, g, 0, 16);
        Art.drawString("XA:" + parent.player.xx, g, 0, 24);
        Art.drawString("YA:" + parent.player.yy, g, 0, 32);
        Art.drawString("FPS:" + ninja.exframes, g, 0, 40);
    }

    @Override
    public void tick(Input input) {
        parent.tick(input);
        if (input.esc.isPressed()) setScreen(parent);
    }

}
