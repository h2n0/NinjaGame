package com.fls.main.entitys;

import java.awt.Graphics;
import java.util.List;

import com.fls.main.art.Sprites;
import com.fls.main.core.achive.Achievment;
import com.fls.main.sound.Audio;

public class PickUp extends Entity {

    private int tex, time = 0;

    public PickUp(double x, double y, int tex) {
        this.x = x;
        this.y = y;
        this.tex = tex;
        this.w = 6;
        this.h = 6;
    }

    public void tick() {
        time++;
        List<Entity> entities = level.getEntitys(x, y, w, h);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e instanceof Player) {
                remove();
                Player player = (Player) e;
                player.pickups++;
                Audio.collect.play();
                level.unlockAchive(Achievment.collect);
            }
        }
        if (time % 10 == 1) tex = random.nextInt(3);
    }

    public void render(Graphics g) {
        g.drawImage(Sprites.parts[1 + tex][0], (int) (x + 4) + (w / 2), (int) (((y + 1) + (h / 2)) + (Math.abs(Math.sin(time * 0.075) * 10))), null);
    }

}
