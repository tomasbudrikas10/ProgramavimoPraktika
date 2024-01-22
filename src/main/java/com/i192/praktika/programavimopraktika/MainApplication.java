package com.i192.praktika.programavimopraktika;

import com.almasb.fxgl.audio.Sound;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

public class MainApplication extends Application {
    SoundManager player = SoundManager.getInstance();


    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        player.setVolume(50);
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(stage);
        sceneManager.setScene(Scenes.MAIN_MENU);
        sceneManager.getStage().getIcons().add(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/logo.png").toURI().toString()));
        sceneManager.getStage().show();
        player.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
