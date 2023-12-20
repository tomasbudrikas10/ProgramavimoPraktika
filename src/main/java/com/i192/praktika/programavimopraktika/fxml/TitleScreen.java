package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.MainApplication;
import javafx.fxml.FXML;

public class TitleScreen {
    @FXML
    public void PlayGameAction(){
        MainApplication.toMainMenu();
    }
}
