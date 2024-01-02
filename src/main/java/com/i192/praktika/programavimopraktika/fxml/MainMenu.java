package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenu {
    @FXML
    public void quitGameAction(ActionEvent actionEvent) {
        MainApplication.QuitApplication();
    }
    @FXML
    public void settingsAction(ActionEvent actionEvent){
        MainApplication.toSettings();
    }

    public void startGameAction(ActionEvent actionEvent) {
        MainApplication.toPlayersJoin();
    }
}
