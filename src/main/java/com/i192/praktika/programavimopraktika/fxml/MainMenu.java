package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu implements Initialisable{

    public Region backgrounRegion;

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
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/backgorund.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BackgroundImage bgImage= new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100,100,true,true,true,true)
        );
        backgrounRegion.backgroundProperty().set(new Background(bgImage));
    }

}
