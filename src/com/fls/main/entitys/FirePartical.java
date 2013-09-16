package com.fls.main.entitys;

import java.awt.Graphics;

import com.fls.main.art.Sprites;

public class FirePartical extends Entity {

    private int life = 0;
    private int tex = 0;

    public FirePartical(int x, int y) {
        this.x = x + 7;
        this.y = y + 4;
        this.w = 3;
        this.h = 4;
        this.yy = 0.12 + (random.nextDouble() * 0.2);
        this.life = random.nextInt(30) + 60;
    }

    public void tick() {
        if (life == 0) die();
        if (life > 0) life--;
        y -= yy;
        if(life % 31 == 1)tex++;
    }

    public void render(Graphics g) {
        g.drawImage(Sprites.parts[3 + tex][3], (int) x, (int) y, null);
    }
}
