package com.i192.praktika.programavimopraktika.spritesheet;

import javafx.scene.paint.Color;

public enum BoxTypes {
    HIT_BOX(Color.RED, "Hit Box"),
    HURT_BOX(Color.BLUE, "Hurt Box"),
    COLLISION_BOX(Color.YELLOW, "Collision Box");
    private Color color;
    private String name;
    BoxTypes(Color color, String name) {
        this.color = color;
        this.name = name;
    }
    public Color getColor() {
        return this.color;
    }
    public String getName() {
        return this.name;
    }
}
