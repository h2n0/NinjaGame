package com.fls.main.entitys;

import java.awt.Graphics;

import com.fls.main.art.Sprites;

public class LightSource extends Entity {

    private int spawnDelay = 0;

    public LightSource(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {
        if (spawnDelay > 0) spawnDelay--;
        if (spawnDelay == 0) {
            spawnDelay = random.nextInt(120) + 60;
            for (int i = 0; i < random.nextInt(4); i++) {
                level.addEntity(new FirePartical((int) x + (i * 4), (int) y));
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Sprites.walls[1][5], (int) x, (int) y, null);
    }
}
