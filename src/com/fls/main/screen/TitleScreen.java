package com.fls.main.screen;

import java.awt.Graphics;

import com.fls.main.art.Sprites;
import com.fls.main.entitys.Entity;
import com.fls.main.entitys.gui.Button;
import com.fls.main.sound.Audio;

import fls.engine.main.Init;
import fls.engine.main.art.Art;
import fls.engine.main.input.Input;

public class TitleScreen extends Screen {
    private int buttonX = 235, buttonY = 175;

    public void render(Graphics g) {
        g.drawImage(Sprites.bgMenu, 0, 0, null);
        for (int i = 0; i < this.entitys.size(); i++) {
            Button e = (Button) entitys.get(i);
            e.render(g);
        }
        Art.drawScaledText("(c) Elliot Lee-Cerrino 2013", g, 6, Init.height * Init.scale - 18, 2);
    }

    public void tick(Input input) {
        if (this.entitys.isEmpty()) {
            add(new Button("Play", buttonX, buttonY));
            add(new Button("Level mkr!", buttonX, buttonY + 32));
            add(new Button("Exit", buttonX, buttonY + (32 * 2)));
        }
        for (int i = 0; i < this.entitys.size(); i++) {
            Button e = (Button) entitys.get(i);
            if (e.id == -1) e.id = i;
            e.tick(input);
            if (e.mouseOver && input.leftMouseButton.isClicked()) getFunction(e.id);
        }
    }

    public void getFunction(int id) {
        switch (id) {
        case 0:
            Audio.collect.play();
            flush();
            setScreen(new GameScreen());
            break;
        case 1:
            flush();
            setScreen(new LevelEditorScreen());
            break;
        case 2:
            ninja.stop();
            break;
        }
    }

    public void add(Entity e) {
        entitys.add(e);
    }

    public void flush() {
        entitys.clear();
    }
}
