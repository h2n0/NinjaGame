package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.fls.main.art.Sprites;

import fls.engine.main.input.Input;

public class LevelEditorScreen extends Screen {

    public int tileX, tileY;
    public byte[] tiles = new byte[25 * 18];
    public int xSpawn, ySpawn;
    private int mouseDelay = 10;

    private boolean containsSpawn = false;

    public void render(Graphics g) {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 18; y++) {
                byte w = tiles[x + y * 25];
                g.drawImage(Sprites.walls[w][0], x * 20, y * 20, null);
                g.setColor(Color.red);
                g.drawRect(x * 20, y * 20, 20, 20);
                if (tileX == x && tileY == y) {
                    g.setColor(Color.green);
                    g.drawRect(x * 20, y * 20, 20, 20);
                }
            }
        }
    }

    public void tick(Input input) {
        tileX = input.mouse.getX() / 20;
        tileY = input.mouse.getY() / 20;
        if (mouseDelay > 0) mouseDelay--;
        if (input.esc.isPressed()) setScreen(new TitleScreen());
        if (input.leftMouseButton.isClicked() && mouseDelay == 0) {
            changeTile(tileX, tileY);
        }
        if (input.rightMouseButton.isClicked() && mouseDelay == 0) {
            setSpawn(tileX, tileY);
        }

        if (input.enter.isPressed() && mouseDelay == 0) {
            saveLevel();
        }
        if (input.space.isPressed()) clear();
    }

    private void changeTile(int tileX, int tileY) {
        mouseDelay = 10;
        if (tiles[tileX + tileY * 25] > 2) {
            tiles[tileX + tileY * 25] = 0;
            return;
        }
        tiles[tileX + tileY * 25] += 1;
    }

    public void setSpawn(int x, int y) {
        tiles[x + y * 25] = 4;
        this.xSpawn = x;
        this.ySpawn = y;
    }

    private void clear() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 18; y++) {
                tiles[x + y * 25] = 0;
            }
        }
    }

    public void saveLevel() {
        mouseDelay = 20;
        boolean done = false;
        int w = 25;
        int h = 18;
        while (!done) {
            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int i = x + y * w;
                    if (tiles[i] == 0) pixels[i] = 0x000000;
                    if (tiles[i] == 1) pixels[i] = 0xFFFFFF;
                    if (tiles[i] == 2) pixels[i] = 0xAD6D00;
                    if (tiles[i] == 3) pixels[i] = 0xFF0000;
                    if (tiles[i] == 4) {
                        pixels[i] = 0xFFFF00;
                        containsSpawn = true;
                    }
                }
            }
            img.setRGB(0, 0, w, h, pixels, 0, w);
            JOptionPane.showMessageDialog(null, null, "Level", 0, new ImageIcon(img.getScaledInstance(w * 20, h * 20, Image.SCALE_AREA_AVERAGING)));
            if (containsSpawn) {
                int amt = 1;
                try {
                    File dir = new File("Levels");
                    if (!dir.exists()) dir.mkdir();
                    File out = new File(dir.getPath() + "/CustomLevel_0.png");
                    while (out.exists()) {
                        out = new File(dir.getPath() + "/CustomLevel_" + amt + ".png");
                        amt++;
                    }
                    ImageIO.write(img, "png", out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            done = true;
        }
    }

}
