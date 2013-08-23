package com.fls.main.core.Tiles;

import java.util.ArrayList;
import java.util.List;

import com.fls.main.core.Level;
import com.fls.main.entitys.Entity;

public class Tile {

    public int tex = 25;
    public boolean solid = false;
    public int x, y;
    public Level level;

    public List<Entity> entitys = new ArrayList<Entity>();

    public boolean blocks(Entity e) {
        return solid;
    }
}
