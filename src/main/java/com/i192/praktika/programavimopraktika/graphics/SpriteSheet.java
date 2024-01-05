package com.i192.praktika.programavimopraktika.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpriteSheet {
    static String sheetsFolderPath = "src/main/resources/com/i192/praktika/programavimopraktika/animation/sprite_sheets/";
    public Image spriteSheet;
    public int columns;
    public int rows;
    public int width;
    public int height;

    public SpriteSheet(String spriteSheetName, int columns, int rows, int width, int height) {
        FileInputStream file;
        try {
            file = new FileInputStream(sheetsFolderPath +  spriteSheetName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.spriteSheet = new Image(file);
        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;
    }

    public ImageView getSpriteIV(int col, int row) {
        PixelReader pixelReader = spriteSheet.getPixelReader();

        WritableImage spriteImage = new WritableImage(pixelReader, col * width, row * height, width, height);

        return new ImageView(spriteImage);
    }

    public Image getSprite(int col, int row) {
        PixelReader pixelReader = spriteSheet.getPixelReader();

        WritableImage spriteImage = new WritableImage(pixelReader, col * width, row * height, width, height);

        return spriteImage;
    }
}
