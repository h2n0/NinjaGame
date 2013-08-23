package com.fls.main.core.achive;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.fls.main.Ninja;
import com.fls.main.art.Sprites;
import com.fls.main.entitys.Entity;

import fls.engine.main.art.Art;

public class Achievment extends Entity {

    public int tex;
    public boolean special;
    public boolean unlocked = false;
    private int yo = -100;
    private int time = 0;
    public String desc;
    public boolean mouseOver = false;
    private BufferedImage achive, bg;
    private boolean waiting = false;

    public static Achievment play = new Achievment(0, "Play time", true);
    public static Achievment jump = new Achievment(1, "Bounce", false);
    public static Achievment collect = new Achievment(2, "Ohh shiny", true);

    public Achievment(int tex, String desc, boolean special) {
        this.tex = tex;
        this.special = special;
        this.unlocked = false;
        this.desc = desc;
        this.time = 0;
    }

    public void tick() {
        if (waiting) {
            lookForSpace();
        } else {
            System.out.println(time);
            if (unlocked && !waiting && time < 101) {
                yo += 1;
                time++;
            }
            if (time >= 100 && time <= 240) time++;
            if (time >= 240) {
                yo -= 1;
                time++;
            }
            if (time >= 300) remove();
        }
    }

    public void render(Graphics g) {
        if (unlocked) {
            Sprites.drawFrame(g, Ninja.width - ((desc.length()) * 10) - 10, yo, (desc.length() * 10), 4);
            g.drawImage(bg.getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING), Ninja.width - (desc.length() + 1 * 6) - 12, yo + 2, null);
            g.drawImage(achive.getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING), Ninja.width - (desc.length() + 1 * 6) - 12, yo + 2, null);
            Art.drawString(desc, g, Ninja.width - (desc.length() + 1 * 6) * 6, yo + 10);
        }
    }

    public void unlock() {
        if (unlocked) return;
        unlocked = true;
        this.achive = Sprites.achive[this.unlocked ? 1 : 0][1 + tex];
        this.bg = Sprites.achive[this.special ? 1 : 0][0];
        for (int i = 0; i < level.entitys.size(); i++) {
            Entity e = level.entitys.get(i);
            if (e instanceof Achievment) {
                if (((Achievment) e).unlocked && !((Achievment) e).waiting) {
                    waiting = true;
                }
                break;
            } else waiting = false;
        }
    }

    public void lookForSpace() {
        for (int i = 0; i < level.entitys.size(); i++) {
            Entity e = level.entitys.get(i);
            if (e instanceof Achievment) {
                if (((Achievment) e).unlocked && ((Achievment) e).waiting) {
                    waiting = false;
                }
                break;
            } else waiting = true;
        }
    }
}
