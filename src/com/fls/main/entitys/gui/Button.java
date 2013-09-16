package com.fls.main.entitys.gui;

import java.awt.Graphics;

import com.fls.main.art.Sprites;
import com.fls.main.entitys.Entity;

import fls.engine.main.input.Input;

public class Button extends Entity {

    protected String title;
    public boolean mouseOver = false;
    public int id = -1;
    private int tx;

    public Button(String title, int x, int y, int w, int tx) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.w = w;
        this.tx = tx;
    }

    public void tick(Input input) {
        int mx = input.mouse.getX();
        int my = input.mouse.getY();
        if (mx >= x && my >= y && mx <= x + ((w + 1) * 24) && my <= y + 24) mouseOver = true;
        else mouseOver = false;
    }

    public void render(Graphics g) {
        int fx = (int) x;
        int fy = (int) y;
        // g.setColor(Color.red);
        Sprites.drawButton(g, title, fx, fy, w, fx + tx, mouseOver);
        // g.drawRect(fx, fy, ((w + 1) * 24), 24);
        // g.setColor(Color.blue);
        // g.drawRect(getEX(), fy, ((w + 1) * 24), 24);
    }
}
