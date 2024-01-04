package com.i192.praktika.programavimopraktika.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet {

    private Image spriteSheet;
    private int columns;
    private int rows;
    private int width;
    private int height;

    public SpriteSheet(String imagePath, int columns, int rows, int width, int height) {
        this.spriteSheet = new Image(imagePath);
        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;
    }

    public ImageView getSprite(int col, int row) {
        PixelReader pixelReader = spriteSheet.getPixelReader();

        WritableImage spriteImage = new WritableImage(pixelReader, col * width, row * height, width, height);

        return new ImageView(spriteImage);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
