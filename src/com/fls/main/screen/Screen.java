package com.fls.main.screen;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.fls.main.Ninja;
import com.fls.main.entitys.Entity;

import fls.engine.main.input.Input;

public abstract class Screen {

    protected Ninja ninja;
    protected Input input;

    protected List<Entity> entitys = new ArrayList<Entity>();

    public abstract void render(Graphics g);

    public abstract void tick();

    public void init(Ninja n,Input input) {
        this.ninja = n;
        this.input = input;
    }

    public void setScreen(Screen screen) {
        ninja.setScreen(screen);
    }

    protected void flush() {
        entitys.clear();
    }

    public void add(Entity e) {
        entitys.add(e);
    }
}
