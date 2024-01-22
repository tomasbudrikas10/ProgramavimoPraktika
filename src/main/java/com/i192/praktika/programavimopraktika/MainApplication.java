package com.i192.praktika.programavimopraktika;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

public class MainApplication extends Application {
    Media sound = new Media(new File("src/main/java/music_bacground/backgroundMusic.m4a").toURI().toString());
    MediaPlayer player = new MediaPlayer(sound);


    @Override
    public void start(Stage stage) throws IOException {

        player.volumeProperty().set(player.volumeProperty().get() * 0.02);
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(stage);
        //sceneManager.setScene(Scenes.SPRITESHEET_EDITOR);
        sceneManager.setScene(Scenes.MAIN_MENU);
        sceneManager.getStage().show();

        player.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
