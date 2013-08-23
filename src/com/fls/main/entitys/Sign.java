package com.fls.main.entitys;

import java.awt.Graphics;
import java.util.List;

import com.fls.main.art.Sprites;

public class Sign extends Entity {

    public int id;
    public boolean autoRead = false;

    public Sign(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.w = 20;
        this.h = 20;
        xx = yy = 0;
        this.id = id;
        autoRead = (id == 1) || (id == 2);
    }

    public void tick() {
        List<Entity> entities = level.getEntitys((int) x, (int) y, 6, 6);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e instanceof Player) {
                Player player = (Player) e;
                player.readSign(this);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Sprites.walls[2][1], (int) x, (int) y, null);
    }
}
