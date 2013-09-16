package com.fls.main.screen;

import com.fls.main.entitys.gui.Button;

public class VariableButton extends Button {

    public boolean selected = false;

    public VariableButton(String title, int x, int y, int w, int tx) {
        super(title, x, y, w, tx);
    }

    public void tick() {
        super.tick();
    }
    
    public void setTitle(String msg){
        title = msg;
    }

    public void onClick() {
        selected = !selected;
    }

}
