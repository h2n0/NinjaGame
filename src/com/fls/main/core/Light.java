package com.fls.main.core;

public class Light {
    private final int x, y;
    public final int brightness, col;

    public Light(int x, int y, int brightness, int col) {
        this.x = x;
        this.y = y;
        this.brightness = brightness;
        this.col = col;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getCol() {
        return col;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
