package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpritesheetEditor implements Initialisable {

    @Override
    public void initialise() {

    }

    public void createOpenSpritesheetWindow(MouseEvent event) {
        Stage popupWindow = createPopup("Open Spritesheet", 300, 200, event);
        Button openFileSearchButton = new Button("Select Spritesheet");
        Pane popupContent = (Pane) popupWindow.getScene().getRoot();
        popupContent.getChildren().add(openFileSearchButton);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Spritesheet");
        openFileSearchButton.setOnMouseClicked(e -> {
            fileChooser.showOpenDialog(popupWindow);
        });
        popupWindow.show();
        System.out.println("Opened spritesheet");
    }

    public void openSpritesheet() {

    }

    public void selectSpritesheet() {

    }

    public Stage createPopup(String title, int width, int height, MouseEvent event) {
        Pane pane = new Pane();
        Scene popupScene = new Scene(pane, width, height);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popupStage.setTitle(title);
        return popupStage;
    }
}
