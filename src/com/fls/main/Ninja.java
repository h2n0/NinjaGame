package com.fls.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.fls.main.screen.Screen;
import com.fls.main.screen.TitleScreen;

import fls.engine.main.Init;
import fls.engine.main.art.Art;
import fls.engine.main.input.Input;

@SuppressWarnings("serial")
public class Ninja extends Init {

    public Input input;
    public Screen screen;
    public JFrame frame;

    private int tFrames;
    private int tUpdates;

    public Ninja() {
        Art.setTextCol(Color.white);
        showFPS();
        setScale(1);
        createWindow("Ninja Game", 500, 360);
        input = new Input(this, Input.BOTH);
        setCreators("Elliot Lee-Cerrino");
        setScreen(new TitleScreen());
        skipInit();
    }

    public void tick() {
        screen.tick();
        tFrames += getFPS();
        tUpdates++;
    }

    public void render(Graphics g) {
        screen.render(g);
    }

    public void setScreen(Screen screen) {
        if (screen != null) screen.init(this, input);
        this.screen = screen;
    }

    public void afterClose() {
        System.out.println("Average ammount of frames: " + (tFrames / tUpdates));
    }

    public static void main(String[] args) {
        Ninja game = new Ninja();
        game.frame = new JFrame(game.title);
        game.frame.add(game);
        game.frame.setResizable(false);
        game.frame.pack();
        game.frame.setVisible(true);
        game.frame.setDefaultCloseOperation(3);
        game.frame.setLocationRelativeTo(null);
        game.start();
    }
}
