package com.fls.main.screen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.fls.main.art.Sprites;
import com.fls.main.entitys.gui.Button;
import com.fls.main.sound.Audio;

import fls.engine.main.Init;
import fls.engine.main.art.Art;

public class TitleScreen extends Screen {
    private int buttonX = 220, buttonY = 175;
    private int time = 0;

    public void render(Graphics g) {
        g.drawImage(Sprites.bgMenu, 0, -340 + time, null);
        for (int i = 0; i < this.entitys.size(); i++) {
            Button e = (Button) entitys.get(i);
            e.render(g);
        }
        if (time >= 340) Art.drawScaledText("(c) Elliot Lee-Cerrino 2013", g, 6, Init.height * Init.scale - 18, 2);
    }

    public void tick() {
        if (time <= 339) time++;
        if (input.keys[KeyEvent.VK_SPACE]) time = 340;
        if (input.keys[KeyEvent.VK_ENTER]) flush();
        if (this.entitys.isEmpty() && time >= 340) {
            add(new Button("Play", buttonX, buttonY, 2, 13));
            add(new Button("Level mkr!", buttonX - 35, buttonY + 34, 5, 14));
            add(new Button("Help", buttonX, buttonY + (34 * 2), 2, 13));
        //    add(new Button("Options", buttonX - 24, buttonY + (34 * 3), 4, 17));
            add(new Button("Exit", buttonX, buttonY + (34 * 3), 2, 13));
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
            setScreen(new LevelEditorScreen(this));
            break;
        case 2:
            flush();
            setScreen(new HelpScreen(this));
            break;
        /**
         * case 3: flush(); setScreen(new OptionsScreen(this)); break;
         **/
        case 3:
            ninja.stop();
            break;
        }
    }
}
