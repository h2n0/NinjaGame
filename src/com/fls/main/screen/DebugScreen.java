package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;

import fls.engine.main.art.Art;

public class DebugScreen extends Screen {

    private GameScreen parent;
    private int key;

    public DebugScreen(GameScreen screen,int key) {
        this.parent = screen;
        this.key = key;
    }

    @Override
    public void render(Graphics g) {
        parent.render(g);
        int x = 200;
        int y = 56;
        g.setColor(Color.black);
        g.fillRect(0, 0, x, y);
        Art.setTextCol(Color.white);
        Art.drawString("X:" + (int) parent.player.x, g, 0, 0);
        Art.drawString("Y:" + (int) parent.player.y, g, 0, 8);
        Art.drawString("onGround:" + parent.player.onGround, g, 0, 16);
        Art.drawString("XA:" + parent.player.xx, g, 0, 24);
        Art.drawString("YA:" + parent.player.yy, g, 0, 32);
        Art.drawString("FPS:" + ninja.exframes, g, 0, 40);
        Art.drawString("Light: "+parent.level.getLightLevel(parent.player.xSlot,parent.player.ySlot), g, 0, 48);
    }

    @Override
    public void tick() {
        parent.tick();
        if (input.keys[key]) setScreen(parent);
    }

}
