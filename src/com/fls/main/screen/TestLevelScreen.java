package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.fls.main.core.Level;
import com.fls.main.entitys.Player;

import fls.engine.main.art.Art;

public class TestLevelScreen extends Screen {

    private Screen parent;
    private Level level;
    private Player player;
    private BufferedImage i;
    private int respawnTime = 0;
    private boolean mayRespawn = false;
    private int photoDelay = 0;

    public TestLevelScreen(Screen p, BufferedImage i) {
        this.parent = p;
        this.i = i;
        level = new Level(this, i);
        player = new Player();
        player.x = level.xSpawn;
        player.y = level.ySpawn;
        level.addEntity(player);
    }

    public void tick() {
        if (photoDelay > 0) photoDelay--;
        boolean up = input.keys[KeyEvent.VK_UP] || input.keys[KeyEvent.VK_W];
        boolean down = input.keys[KeyEvent.VK_DOWN] || input.keys[KeyEvent.VK_S];
        boolean left = input.keys[KeyEvent.VK_LEFT] || input.keys[KeyEvent.VK_A];
        boolean right = input.keys[KeyEvent.VK_RIGHT] || input.keys[KeyEvent.VK_D];
        boolean jump = input.keys[KeyEvent.VK_COMMA] || input.keys[KeyEvent.VK_Z];
        boolean attack = input.keys[KeyEvent.VK_PERIOD] || input.keys[KeyEvent.VK_X];
        boolean photo = input.keys[KeyEvent.VK_0];
        if (input.keys[KeyEvent.VK_ESCAPE]) setScreen(parent);
        if (input.keys[KeyEvent.VK_ENTER] && mayRespawn) respawnRoom();
        level.tick();
        if (respawnTime > 20) mayRespawn = true;
        if (!player.removed) player.tick(up, down, left, right, jump, attack);
        else {
            respawnTime++;
            if (respawnTime >= 20) {
                mayRespawn = true;
            }
        }
        if (photo & photoDelay == 0) {
            photoDelay = 60;
            Art.saveScreenShot(ninja);
        }
    }

    public void respawnRoom() {
        Level newLevel = new Level(this, i);
        this.level = newLevel;
        player.removed = false;
        player.x = level.xSpawn - 10;
        player.y = level.ySpawn - 10;
        level.addEntity(player);
        mayRespawn = false;
        respawnTime = 0;
    }

    public void render(Graphics g) {
        level.render(g);
        if (mayRespawn) {
            Art.setTextCol(Color.black);
            Art.drawScaledText("Press enter to respawn", g, 100, 100, 2);
        } else Art.setTextCol(Color.white);
    }
}
