package com.fls.main.screen;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.fls.main.Ninja;
import com.fls.main.entitys.Entity;

import fls.engine.main.input.Input;

public abstract class Screen {

    protected Ninja ninja;

    protected List<Entity> entitys = new ArrayList<Entity>();

    public abstract void render(Graphics g);

    public abstract void tick(Input input);

    public void init(Ninja n) {
        this.ninja = n;
    }

    public void setScreen(Screen screen) {
        ninja.setScreen(screen);
    }
}
