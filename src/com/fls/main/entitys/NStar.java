package com.fls.main.entitys;

import java.awt.Graphics;
import java.awt.Image;

import com.fls.main.art.Sprites;
import com.fls.main.core.Level;

public class NStar extends Entity {

    private int life = 0;

    public NStar(double x, double y, double xa, double ya) {
        this.x = x;
        this.y = y;
        this.xx = xa + (random.nextDouble() - random.nextDouble()) * 0.5;
        this.yy = ya + (random.nextDouble() - random.nextDouble()) * 0.5;
        this.interactsWithWorld = true;
    }

    public void tick() {
        tryMove(xx, yy);
        if (life >= 100) remove();
        xx *= 0.6;
        yy *= 0.6;
        yy += Level.gravity;
        life++;
    }

    public void hitWall(double x, double y) {
        remove();
    }

    public void render(Graphics g) {
        int xp = (int) x;
        int yp = (int) y;
        g.drawImage(Sprites.parts[(life / 10) % 2][7].getScaledInstance(12, 12, Image.SCALE_AREA_AVERAGING), xp, yp, null);
    }
}
