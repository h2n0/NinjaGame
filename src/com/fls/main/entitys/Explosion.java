package com.fls.main.entitys;

import java.awt.Graphics;
import java.util.List;

import com.fls.main.art.Sprites;
import com.fls.main.core.Level;

public class Explosion extends Entity {
    private int life, delay, color;
    private int duration;
    public int power;

    public Explosion(int power, int delay, double x, double y, double xa, double ya) {
        this.power = power;
        this.x = x;
        this.y = y;
        this.w = 1;
        this.h = 1;
        bounce = 0.2;
        this.xx = (xa + (random.nextDouble() - random.nextDouble()) * 0.2);
        this.yy = (ya + (random.nextDouble() - random.nextDouble()) * 0.2);

        color = random.nextInt(3);

        duration = random.nextInt(20) + 10;
        life = 0;
    }

    public void tick() {
        if (delay > 0) {
            delay--;
            return;
        }
        if (life++ >= duration) remove();
        interactsWithWorld = (life > 10);
        onGround = false;
        // tryMove(xa, ya);
        x += xx;
        y += yy;

        level.isFree(this, x, y, w, h, 0, 0);
        xx *= 0.95;
        yy *= 0.95;
        yy -= Level.gravity * 0.15;

        if (interactsWithWorld && life < duration * 0.75) {
            List<Entity> entities = level.getEntitys((int) x, (int) y, 1, 1);
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).explode(this);
            }
        }
    }

    public void hitWall(double xa, double ya) {
        this.xx *= 0.4;
        this.yy *= 0.4;
    }

    public void render(Graphics g) {
        int xp = (int) x;
        int yp = (int) y;
        g.drawImage(Sprites.parts[(life - 1) * 8 / duration][4 + color], xp - 3, yp - 3, null);
    }
}
