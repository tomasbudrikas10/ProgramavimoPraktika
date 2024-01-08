package com.i192.praktika.programavimopraktika;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(stage);
        sceneManager.setScene(Scenes.MAIN_MENU);
        sceneManager.getStage().show();
        Image image =new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/animations/sprite_sheets/background.png"));
        BackgroundImage bgImage= new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100,100,true,true,true,true)
        );
        Background bg= new Background(bgImage);

    }

    public static void main(String[] args) {
        launch();
    }
}