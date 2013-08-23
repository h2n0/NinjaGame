package com.fls.main.entitys;

import java.awt.Graphics;
import java.util.Random;

import com.fls.main.core.Level;
import com.fls.main.core.Tiles.Tile;

public class Entity {

    public Level level;
    public double x, y;
    public boolean onGround = false;
    public boolean canClimb = false;
    public boolean againstWall = false;
    public boolean removed = false;
    public boolean interactsWithWorld;
    public double r, xx, yy;
    public double fallSpeed = 0;
    protected Tile tile;
    protected double speed;
    protected double bounce = 0.05;
    public int w, h;
    public int xSlot, ySlot;

    protected Random random = new Random();

    public Entity() {
        r = 0.4;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public void remove() {
        removed = true;
    }

    public void tryMove(double xa, double ya) {
        canClimb = false;
        onGround = false;
        againstWall = false;
        if (level.isFree(this, x + xa, y, w, h, xa, 0)) {
            // if (speed < 4) speed += 0.023;
            x += xa;
        } else {
            hitWall(xa, 0);
            if (xa < 0) {
                double xx = x / 20;
                xa = -(xx - ((int) xx)) * 20;
            } else {
                double xx = (x + w) / 20;
                xa = 20 - (xx - ((int) xx)) * 20;
            }
            if (level.isFree(this, x + xa, y, w, h, xa, 0)) {
                x += xa;
            }
            againstWall = true;
            this.xx *= -bounce;
        }
        if (level.isFree(this, x, y + ya, w, h, 0, ya)) {
            y += ya;
        } else {
            hitWall(0, ya);
            if (ya > 0) onGround = true;
            if (ya < 0) {
                double yy = y / 20;
                ya = -(yy - ((int) yy)) * 20;
            } else {
                double yy = (y + h) / 20;
                ya = 20 - (yy - ((int) yy)) * 20;
            }
            if (level.isFree(this, x, y + ya, w, h, 0, ya)) {
                y += ya;
            }
            this.yy *= -bounce;
        }
    }

    public void hitWall(double x, double y) {

    }

    public void explode(Explosion e) {

    }

    public void die() {
        remove();
    }

    public void outOfBounds() {

    }

    public void hitSpikes() {
        die();
    }
}
