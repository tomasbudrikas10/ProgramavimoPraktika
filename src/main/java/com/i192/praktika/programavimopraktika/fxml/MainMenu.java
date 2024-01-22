package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu implements Initialisable{
    public ImageView backgroundImageView;
    public StackPane root;

    @FXML
    public void quitGameAction() throws IOException {
        SceneManager.getInstance().getStage().close();
    }
    @FXML
    public void settingsAction() throws IOException {
        SceneManager.getInstance().setScene(Scenes.SETTINGS);
    }

    @FXML
    public void startGameAction() throws IOException {
        SceneManager.getInstance().setScene(Scenes.PLAYERS_JOIN);
    }

    @Override
    public void initialise() {
        backgroundImageView.fitWidthProperty().bind(root.widthProperty());
        backgroundImageView.fitHeightProperty().bind(root.heightProperty());
    }

}
