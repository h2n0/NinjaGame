package com.fls.main.entitys.gui;

import java.awt.Graphics;

import com.fls.main.art.Sprites;
import com.fls.main.entitys.Entity;

import fls.engine.main.art.Art;
import fls.engine.main.input.Input;

public class Button extends Entity {

    private String title;
    public boolean mouseOver = false;
    public int id = -1;

    public Button(String title, int x, int y) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.w = 1 + (title.length() / 5);
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
        int tx = getEX();
        // g.setColor(Color.red);
        Sprites.drawButton(g, title, fx, fy, w, mouseOver);
        Art.setTextCol(0xffffff);
        Art.drawString(title, g, tx, fy + 8);
        // g.drawRect(fx, fy, ((w + 1) * 24), 24);
        // g.setColor(Color.blue);
        // g.drawRect(getEX(), fy, ((w + 1) * 24), 24);
    }

    public int getEX() {
        return (int) x + (w * 24) / 4;// ((x + (w + 1) * 24));
    }
}
