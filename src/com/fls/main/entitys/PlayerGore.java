package com.fls.main.entitys;

import java.awt.Graphics;

import com.fls.main.art.Sprites;
import com.fls.main.core.Level;

public class PlayerGore extends Entity {
    private int life;

    public PlayerGore(double x, double y) {
        this.x = x;
        this.y = y;
        this.w = 2;
        this.h = 2;
        bounce = 0.8;
        this.xx = 0 + (random.nextDouble() - random.nextDouble()) * 1.5;
        this.yy = -1 + (random.nextDouble() - random.nextDouble()) * 1.5;

        life = random.nextInt(90) + 60;
    }

    public void tick() {
        if (life-- <= 0) remove();
        onGround = false;
        tryMove(xx, yy);

        xx *= Level.friction;
        yy *= Level.friction;
        yy += Level.gravity * 0.5;
        level.addEntity(new Gore(x + random.nextDouble(), y + random.nextDouble() - 1, xx, yy));
    }

    public void hitWall(double xa, double ya) {
        this.xx *= 0.9;
        this.yy *= 0.9;
    }

    public void render(Graphics g) {
        int xp = (int) x;
        int yp = (int) y;
        g.drawImage(Sprites.parts[0][0], xp, yp, null);
    }

    public void hitSpikes() {
        for (int i = 0; i < 4; i++) {
            xx = (random.nextFloat()-random.nextFloat())*6;
            yy = (random.nextFloat()-random.nextFloat())*6;
            level.addEntity(new Gore(x + random.nextDouble(), y + random.nextDouble() - 1, xx, yy));
        }
        remove();
    }

}
