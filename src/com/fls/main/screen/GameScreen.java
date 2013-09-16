package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.fls.main.core.Level;
import com.fls.main.core.Stats;
import com.fls.main.core.achive.Achievment;
import com.fls.main.entitys.Player;

import fls.engine.main.art.Art;

public class GameScreen extends Screen {

    public Player player;

    public int xLevel = 0, yLevel = 0;
    private int respawnTime = 0;
    public Level level = new Level(this, 25, 18, xLevel, yLevel, 0, 0);
    public int screenChangedelay = 10;
    private int photoDelay = 0;
    private int debugkey = KeyEvent.VK_BACK_SLASH;

    private boolean mayRespawn = false;

    public GameScreen() {
        player = new Player();
        player.x = level.xSpawn;
        player.y = level.ySpawn;
        level.addEntity(player);
        Stats.reset();
        level.unlockAchive(Achievment.play);
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
        Stats.instance.time++;
        if (screenChangedelay > 0) screenChangedelay--;
        if (input.keys[debugkey]) setScreen(new DebugScreen(this, debugkey));
        level.tick();
        if (!player.removed) {
            player.tick(up, down, left, right, jump, attack);
        } else {
            respawnTime++;
            if (respawnTime >= 20) {
                mayRespawn = true;
            }
        }

        if (mayRespawn && input.keys[KeyEvent.VK_ENTER]) {
            respawnRoom();
            mayRespawn = false;
            Art.setTextCol(Color.white);
        }

        if (photo & photoDelay == 0) {
            photoDelay = 60;
            Art.saveScreenShot(ninja);
        }
    }

    public void render(Graphics g) {
        level.render(g);
        if (mayRespawn) {
            Art.setTextCol(Color.black);
            Art.drawScaledText("Dead", g, 100, 100, 4);
            Art.drawScaledText("Press ENTER to respawn", g, 100, 130, 2);
        }
    }

    public void trans(int x, int y) {
        xLevel += x;
        yLevel += y;
        if (xLevel > 1) {
            setScreen(new WinScreen());
            return;
        }
        player.x -= x * 500;
        player.y -= y * 360;
        level.removeEntity(player);
        Level newLevel = new Level(this, 25, 18, xLevel, yLevel, (int) player.x, (int) (player.y + y * 5));
        newLevel.addEntity(player);
        this.level = newLevel;
    }

    public void respawnRoom() {
        Level newLevel = new Level(this, 25, 18, xLevel, yLevel, level.xSpawn, level.ySpawn);
        this.level = newLevel;
        player.removed = false;
        player.x = level.xSpawn - 10;
        player.y = level.ySpawn - 10;
        level.addEntity(player);
    }

    public void readSign(int id) {
        if (screenChangedelay == 0) {
            setScreen(new MessageScreen(this, id));
            screenChangedelay = 10;
        }
    }
}
