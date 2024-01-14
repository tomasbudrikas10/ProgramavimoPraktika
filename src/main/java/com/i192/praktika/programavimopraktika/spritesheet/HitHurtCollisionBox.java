package com.i192.praktika.programavimopraktika.spritesheet;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitHurtCollisionBox {
    private BoxTypes type;
    private Color color;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;
    private int xVelocity;
    private int yVelocity;
    public HitHurtCollisionBox(BoxTypes type, int width, int height) {
        this.type = type;
        this.color = type.getColor();
        this.width = width;
        this.height = height;
        this.xVelocity = 0;
        this.yVelocity = 0;
    }
    public String getName() {
        return this.type.getName();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public Color getColor() {
        return color;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
