package com.i192.praktika.programavimopraktika;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(stage);
        sceneManager.setScene(Scenes.MAIN_MENU);
        sceneManager.getStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}