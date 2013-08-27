package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.fls.main.art.Sprites;

import fls.engine.main.input.Input;

public class LevelEditorScreen extends Screen {

    public int tileX, tileY;
    public byte[] tiles = new byte[25 * 18];
    public int xSpawn = -1, ySpawn = -1;
    private int mouseDelay = 10;
    private int changeDelay = 0;

    private boolean containsSpawn = false;
    private BufferedImage level;

    public LevelEditorScreen() {
        setBounds();
    }

    public void render(Graphics g) {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 18; y++) {
                byte w = tiles[x + y * 25];
                g.drawImage(Sprites.levelt[w % 4][w / 4], x * 20, y * 20, null);
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
        if (changeDelay > 0) changeDelay--;
        tileX = input.mouse.getX() / 20;
        tileY = input.mouse.getY() / 20;
        if (mouseDelay > 0) mouseDelay--;
        if (input.esc.isPressed() && changeDelay == 0) setScreen(new TitleScreen());
        if (input.leftMouseButton.isClicked() && mouseDelay == 0) {
            changeTile(tileX, tileY);
        }
        if (input.rightMouseButton.isClicked() && mouseDelay == 0) {
            setSpawn(tileX, tileY);
        }

        if (input.shift.isPressed()) {
            saveLevel();
        }

        if (input.enter.isPressed() && containsSpawn) {
            generateLevel();
            if (level == null) {
                System.err.println("Level not found");
                return;
            }
            changeDelay = 10;
            setScreen(new TestLevelScreen(this, level));
        }
        if (input.space.isPressed()) clear();
    }

    private void changeTile(int tileX, int tileY) {
        mouseDelay = 10;
        if (tiles[tileX + tileY * 25] > 9) {
            tiles[tileX + tileY * 25] = 0;
            return;
        }
        tiles[tileX + tileY * 25] += 1;
    }

    public void setSpawn(int x, int y) {
        if (xSpawn == -1 && ySpawn == -1) tiles[x + y * 25] = 11;
        this.xSpawn = x;
        this.ySpawn = y;
        this.containsSpawn = true;
    }

    private void setBounds() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 18; y++) {
                if (x == 0 || y == 0 || x == 24 || y == 17) {
                    tiles[x + y * 25] = 1;
                }
            }
        }
    }

    private void clear() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 18; y++) {
                tiles[x + y * 25] = 0;
            }
        }
    }

    public void generateLevel() {
        mouseDelay = 20;
        int w = 25;
        int h = 18;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;
                if (tiles[i] == 0) pixels[i] = 0x000000; // backGround
                else if (tiles[i] == 1) pixels[i] = 0xFFFFFF; // Walls
                else if (tiles[i] == 2) pixels[i] = 0xAD6D00; // Floor
                else if (tiles[i] == 3) pixels[i] = 0xFF0000; // TNT
                else if (tiles[i] == 4) pixels[i] = 0x007700; // Ladder
                else if (tiles[i] == 5) pixels[i] = 0x00FF00; // Spikes
                // if (tiles[i] == 6) pixels[i] = 0xff00ff; // Sign
                else if (tiles[i] == 7) pixels[i] = 0xFFAA00; // Lava
                else if (tiles[i] == 8) pixels[i] = 0xFF00FF; // --> Convayor
                else if (tiles[i] == 9) pixels[i] = 0x9E009E; // <-- Convayor
                else if (tiles[i] == 10) pixels[i] = 0xBB00BB; // ^ Convayor
                else if (tiles[i] == 11) {
                    pixels[i] = 0xFFFF00;
                    containsSpawn = true;
                } else {
                    pixels[i] = 0x000000;
                }
            }
        }
        img.setRGB(0, 0, w, h, pixels, 0, w);
        // JOptionPane.showMessageDialog(null, null, "Level", 0, new ImageIcon(img.getScaledInstance(w * 20, h * 20, Image.SCALE_AREA_AVERAGING)));
        level = img;
    }

    private void saveLevel() {
        if (level == null) return;
        int amt = 1;
        try {
            File dir = new File("Levels");
            if (!dir.exists()) dir.mkdir();
            File out = new File(dir.getPath() + "/CustomLevel_0.png");
            while (out.exists()) {
                out = new File(dir.getPath() + "/CustomLevel_" + amt + ".png");
                amt++;
            }
            ImageIO.write(level, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
