package com.fls.main.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fls.main.art.Sprites;
import com.fls.main.core.achive.Achievment;
import com.fls.main.entitys.Entity;
import com.fls.main.entitys.Explosion;
import com.fls.main.entitys.PickUp;
import com.fls.main.entitys.Sign;
import com.fls.main.screen.GameScreen;
import com.fls.main.screen.TestLevelScreen;

public class Level {

    public byte[] tiles;
    public int width, height;
    public List<Entity> entitys = new ArrayList<Entity>();
    public List<Entity>[] entityMap;
    public int xSpawn, ySpawn;
    private int ticks = 0;
    public static final double gravity = 0.10;
    public static final double friction = 0.99;
    public Random random = new Random();
    private GameScreen screen;
    private TestLevelScreen s2;

    @SuppressWarnings("unchecked")
    public Level(TestLevelScreen s, BufferedImage i) {
        this.s2 = s;
        this.width = 25;
        this.height = 18;
        int[] pixels = new int[width * height];

        i.getRGB(0, 0, width, height, pixels, 0, width);

        tiles = new byte[width * height];
        entityMap = new ArrayList[width * height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                entityMap[x + y * width] = new ArrayList<Entity>();
                int col = pixels[x + y * width] & 0xffffff;
                byte ww = 0;

                if (col == 0xFFFFFF) {
                    ww = 1;
                } else if (col == 0xAD6D00) {
                    ww = 2;
                } else if (col == 0xFFFF00) {
                    this.xSpawn = x * 20;
                    this.ySpawn = y * 20;
                } else if (col == 0xFF00FF) { // --> Convayor
                    ww = 3;
                } else if (col == 0x9E009E) { // <-- Convayor
                    ww = 4;
                } else if (col == 0xBB00BB) { // ^ Convayor
                    ww = 5;
                } else if (col == 0x007700) { // Ladder
                    ww = 6;
                } else if (col == 0x00FF00) { // Spikes
                    ww = 7;
                } else if ((col & 0x00ffff) == 0x00ff00 && (col & 0xff0000) > 0) { // Sign
                    addEntity(new Sign(x * 20, y * 20, (col >> 16) & 0xff));
                } else if (col == 0x00FFFF) { // PickUp
                    addEntity(new PickUp(x * 20, y * 20, random.nextInt(2)));
                } else if (col == 0xFF0000) {
                    ww = 8;
                } else if (col == 0xFFAA00) {
                    ww = 9;
                }
                tiles[x + y * width] = ww;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Level(GameScreen screen, int w, int h, int xOff, int yOff, int xSpawn, int ySpawn) {
        this.screen = screen;
        this.width = w;
        this.height = h;
        int[] pixels = new int[25 * 18];

        Sprites.level.getRGB(xOff * 25, yOff * 18, 25, 18, pixels, 0, 25);

        tiles = new byte[w * h];
        entityMap = new ArrayList[w * h];

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                entityMap[x + y * width] = new ArrayList<Entity>();
                int col = pixels[x + y * w] & 0xffffff;
                byte ww = 0;

                if (col == 0xFFFFFF) {
                    ww = 1;
                } else if (col == 0xAD6D00) {
                    ww = 2;
                } else if (col == 0xFFFF00) {
                    this.xSpawn = x * 20;
                    this.ySpawn = y * 20;
                } else if (col == 0xFF00FF) { // --> Convayor
                    ww = 3;
                } else if (col == 0x9E009E) { // <-- Convayor
                    ww = 4;
                } else if (col == 0xBB00BB) { // ^ Convayor
                    ww = 5;
                } else if (col == 0x007700) { // Ladder
                    ww = 6;
                } else if (col == 0x00FF00) { // Spikes
                    ww = 7;
                } else if ((col & 0x00ffff) == 0x00ff00 && (col & 0xff0000) > 0) { // Sign
                    addEntity(new Sign(x * 20, y * 20, (col >> 16) & 0xff));
                } else if (col == 0x00FFFF) { // PickUp
                    addEntity(new PickUp(x * 20, y * 20, random.nextInt(2)));
                } else if (col == 0xFF0000) {
                    ww = 8;
                } else if (col == 0xFFAA00) {
                    ww = 9;
                }
                tiles[x + y * w] = ww;
            }
        }
    }

    public void tick() {
        ticks++;
        for (int i = 0; i < entitys.size(); i++) {
            Entity e = entitys.get(i);
            int xSlotOld = e.xSlot;
            int ySlotOld = e.ySlot;
            if (!e.removed) e.tick();
            e.xSlot = (int) ((e.x + e.w / 2.0) / 20);
            e.ySlot = (int) ((e.y + e.h / 2.0) / 20);
            if (e.removed) {
                if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                    entityMap[xSlotOld + ySlotOld * width].remove(e);
                }
                entitys.remove(i--);
            } else {
                if (e.xSlot != xSlotOld || e.ySlot != ySlotOld) {
                    if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                        entityMap[xSlotOld + ySlotOld * width].remove(e);
                    }
                    if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
                        entityMap[e.xSlot + e.ySlot * width].add(e);
                    } else {
                        e.outOfBounds();
                    }

                }
            }
        }
    }

    public void render(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                byte w = tiles[x + y * width];
                int xImg = 0, yImg = 0;
                switch (w) {
                default:
                    xImg = 0;
                    break;
                case 1:
                    xImg = 1;
                    break;
                case 2:
                    xImg = 2;
                    break;
                case 3:
                    yImg = 2;
                    xImg = (ticks / 4 + x * 2) & 3;
                    break;
                case 4:
                    yImg = 3;
                    xImg = (-ticks / 4 + x * 2) & 3;
                    break;
                case 5:
                    yImg = 4;
                    xImg = (-ticks / 4 + x * 2) & 3;
                    break;
                case 6:
                    yImg = 1;
                    break;
                case 7:
                    yImg = xImg = 1;
                    break;
                case 8:
                    xImg = 3;
                    break;
                case 9:
                    yImg = 1;
                    xImg = 3;
                    break;
                }
                g.drawImage(Sprites.walls[xImg][yImg], x * 20, y * 20, null);
            }
        }
        for (int i = 0; i < entitys.size(); i++) {
            Entity e = entitys.get(i);
            e.render(g);
        }
    }

    private List<Entity> hits = new ArrayList<Entity>();

    public List<Entity> getEntitys(double xc, double yc, double w, double h) {
        hits.clear();
        int r = 20 - (int) w;
        int x0 = (int) ((xc - r) / 20);
        int y0 = (int) ((yc - r) / 20);
        int x1 = (int) ((xc + w + r) / 20);
        int y1 = (int) ((yc + h + r) / 20);
        for (int x = x0; x <= x1; x++)
            for (int y = y0; y <= y1; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    List<Entity> es = entityMap[x + y * width];
                    for (int i = 0; i < es.size(); i++) {
                        Entity e = es.get(i);
                        double xx0 = e.x;
                        double yy0 = e.y;
                        double xx1 = e.x + e.w;
                        double yy1 = e.y + e.h;
                        if (xx0 > xc + w || yy0 > yc + h || xx1 < xc || yy1 < yc) continue;
                        hits.add(e);
                    }
                }
            }
        return hits;
    }

    public boolean isFree(Entity e, double xc, double yc, int w, int h, double xa, double ya) {
        if (e.interactsWithWorld) return isBulletFree(e, xc, yc, w, h);
        double r = 0.1;
        int x0 = (int) (xc / 20);
        int y0 = (int) (yc / 20);
        int x1 = (int) ((xc + w - r) / 20);
        int y1 = (int) ((yc + h - r) / 20);

        boolean move = true;

        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    byte ww = tiles[x + y * width];
                    if (ww != 0) move = false;
                    if (ww == 3) e.xx += 0.12;
                    if (ww == 4) e.xx -= 0.12;
                    if (ww == 5) e.yy -= 0.12;
                    if (ww == 6) {
                        e.canClimb = true;
                        move = true;
                    }
                    if (ww == 7 && ya != 0) e.hitSpikes();
                    if (ww == 9) e.die();
                }
            }
        }
        return move;
    }

    public boolean isBulletFree(Entity e, double xc, double yc, int w, int h) {
        double r = 0.1;
        int x0 = (int) (xc / 20);
        int y0 = (int) (yc / 20);
        int x1 = (int) ((xc + w - r) / 20);
        int y1 = (int) ((yc + h - r) / 20);
        boolean ok = true;
        for (int x = x0; x <= x1; x++)
            for (int y = y0; y <= y1; y++) {
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    byte ww = tiles[x + y * width];
                    if (ww != 0) ok = false;
                    if (ww == 8) {
                        for (int i = 0; i < 32; i++) {
                            double dir = i * Math.PI * 2 / 8.0;
                            double xa = Math.sin(dir);
                            double ya = Math.cos(dir);
                            double dist = (i / 8) + 1;
                            addEntity(new Explosion(1, i * 3, x * 20 + 5 + xa * dist, y * 20 + 5 + ya * dist, xa, ya));
                            tiles[x + y * width] = 0;
                        }
                    }
                }
            }
        return ok;

    }

    public void readSign(Sign sign) {
        screen.readSign(sign.id - 1);
    }

    public void trans(int x, int y) {
        if (screen != null) screen.trans(x, y);
    }

    public void addEntity(Entity e) {
        e.level = this;
        entitys.add(e);
    }

    public void removeEntity(Entity e) {
        entitys.remove(e);
    }

    public void unlockAchive(Achievment a) {
        a.level = this;
        a.unlock();
        entitys.add(a);
    }
}
