package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenu {
    @FXML
    public void quitGameAction() throws IOException {
        SceneManager.getInstance().getStage().close();
    }
    @FXML
    public void settingsAction() throws IOException {
        SceneManager.getInstance().setScene("settings");
    }

    @FXML
    public void startGameAction() throws IOException {
        SceneManager.getInstance().setScene("players join");
    }
}
