package com.fls.main.screen;

import java.awt.Color;
import java.awt.Graphics;

import com.fls.main.core.Level;
import com.fls.main.core.Stats;
import com.fls.main.core.achive.Achievment;
import com.fls.main.entitys.Player;

import fls.engine.main.art.Art;
import fls.engine.main.input.Input;

public class GameScreen extends Screen {

    public Player player;

    public int xLevel = 0, yLevel = 0;
    private int respawnTime = 0;
    public Level level = new Level(this, 25, 18, xLevel, yLevel, 0, 0);
    public int screenChangedelay = 10;

    private boolean mayRespawn = false;

    public GameScreen() {
        player = new Player();
        player.x = level.xSpawn;
        player.y = level.ySpawn;
        level.addEntity(player);
        Stats.reset();
        level.unlockAchive(Achievment.play);
    }

    public void tick(Input input) {
        Stats.instance.time++;
        if (screenChangedelay > 0) screenChangedelay--;
        if (input.esc.isPressed()) setScreen(new DebugScreen(this));
        level.tick();
        if (!player.removed) {
            player.tick(input.up.isPressed(), input.down.isPressed(), input.left.isPressed(), input.right.isPressed(), input.space.isPressed(), input.shift.isPressed());
        } else {
            respawnTime++;
            if (respawnTime >= 20) {
                mayRespawn = true;
            }
        }

        if (mayRespawn && input.enter.isPressed()) {
            respawnRoom();
            mayRespawn = false;
            Art.setTextCol(Color.white);
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
