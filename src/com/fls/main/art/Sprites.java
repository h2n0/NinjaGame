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
    public static BufferedImage level = load("/Level2.png");
    public static BufferedImage bgMenu = load("/menu/BG.png");

    public static void drawFrame(Graphics g, int ix, int iy, int ex, int ey) {
        for (int x = 0 - 1; x < ex + 1; x++) {
            for (int y = 0; y < ey + 1; y++) {
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

    public static void drawButton(Graphics g, String title, int ix, int iy, int ey, boolean hilighted) {
        for (int i = 0; i < ey + 1; i++) {
            int xImg = 4;
            if (i == 0) xImg--;
            if (i >= ey) xImg++;
            g.drawImage(parts[xImg][hilighted ? 2 : 1].getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING), ix + i * 24, iy, null);
        }

    }
}
