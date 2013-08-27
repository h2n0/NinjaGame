package com.fls.main.art;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import fls.engine.main.art.Art;

public class Sprites extends Art {

    public static BufferedImage[][] walls = split(load("/tex/Sprites.png"), 20, 20);
    public static BufferedImage[][] entitys = split(load("/tex/Entitys.png"), 30, 30);
    public static BufferedImage[][] entitys2 = altsplit(load("/tex/Entitys.png"), 30, 30);
    public static BufferedImage[][] parts = split(load("/tex/particals.png"), 6, 6);
    public static BufferedImage[][] achive = split(load("/tex/achivments.png"), 12, 12);
    public static BufferedImage[][] levelt = split(load("/tex/LevelEditorSprites.png"), 20, 20);
    public static BufferedImage level = load("/Level.png");
    public static BufferedImage bgMenu = load("/menu/BG.png");
    public static BufferedImage texLogo;

    public static void drawFrame(Graphics g, int ix, int iy, int ex, int ey) {
        for (int x = 0 - 1; x < ex + 1; x++) {
            for (int y = 0 - 1; y < ey + 1; y++) {
                int xf = 1;
                int yf = 2;
                if (x < 0) xf--;
                if (y < 0) yf--;
                if (x >= ex) xf++;
                if (y >= ey) yf++;
                g.drawImage(Sprites.parts[xf][yf], ix + x * 6, iy + y * 6, null);
            }
        }
    }

    public static void drawButton(Graphics g, String title, int ix, int iy, int ex, int tx, boolean hilighted) {
        for (int i = 0; i < ex + 1; i++) {
            int xImg = 4;
            if (i == 0) xImg--;
            if (i >= ex) xImg++;
            g.drawImage(parts[xImg][hilighted ? 2 : 1].getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING), ix + i * 24, iy, null);
            Art.drawScaledText(title, g, tx, iy + 5, 2);
        }

    }

    public static void reload(String alt) {
        walls = split(load(alt + "/tex/Sprites.png"), 20, 20);
        entitys = split(load(alt + "/tex/Entitys.png"), 30, 30);
        entitys2 = altsplit(load(alt + "/tex/Entitys.png"), 30, 30);
        parts = split(load(alt + "/tex/particals.png"), 6, 6);
        achive = split(load(alt + "/tex/achivments.png"), 12, 12);
        level = load(alt + "/Level.png");
        bgMenu = load(alt + "/menu/BG.png");
    }
}
