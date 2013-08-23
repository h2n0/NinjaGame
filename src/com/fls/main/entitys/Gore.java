package com.fls.main.entitys;

import java.awt.Graphics;

import com.fls.main.art.Sprites;
import com.fls.main.core.Level;

public class Gore extends Entity {

    private int life;

    public Gore(double x, double y, double xa, double ya) {
        this.x = x;
        this.y = y;
        this.w = 1;
        this.h = 1;
        bounce = 0.2;
        this.xx = (xa + (random.nextDouble() - random.nextDouble()) * 1) * 0.2;
        this.yy = (ya + (random.nextDouble() - random.nextDouble()) * 1) * 0.2;

        life = random.nextInt(20) + 10;
    }

    public void tick() {
        if (life-- <= 20) remove();
        onGround = false;
        tryMove(xx, yy);
        xx *= 0.999;
        yy *= 0.999;
        yy += Level.gravity * 0.15;
    }

    public void render(Graphics g) {
        int xp = (int) x;
        int yp = (int) y;
        g.drawImage(Sprites.parts[0][0], xp, yp, null);
    }

    public void hitWall(double x, double y) {
        this.xx *= 0.4;
        this.yy *= 0.4;
    }
}
