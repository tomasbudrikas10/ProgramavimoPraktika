package com.i192.praktika.programavimopraktika;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenuViewController {
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    @FXML
    private void handleQuitButtonClick() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }
}
