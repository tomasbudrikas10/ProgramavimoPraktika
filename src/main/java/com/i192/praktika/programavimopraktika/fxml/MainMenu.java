package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenu implements Initialisable{
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

    }
}
