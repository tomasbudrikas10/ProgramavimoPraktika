package com.i192.praktika.programavimopraktika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(stage);
        sceneManager.loadFXMLScene("main-menu", "main menu", "style");
        sceneManager.loadFXMLScene("character-select", "character select");
        sceneManager.loadFXMLScene("settings", "settings");
        sceneManager.loadFXMLScene("input-settings", "input settings");
        sceneManager.loadFXMLScene("players-join", "players join");
        sceneManager.loadFXMLScene("gamemode-select", "gamemode select");
        sceneManager.setScene("main menu");
        sceneManager.getStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}