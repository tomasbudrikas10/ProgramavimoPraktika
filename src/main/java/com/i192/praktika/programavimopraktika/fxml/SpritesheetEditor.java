package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class SpritesheetEditor implements Initialisable {
    public ImageView openedSpritesheet;
    public ScrollPane spritesheetScrollpane;

    @Override
    public void initialise() {
        openedSpritesheet = new ImageView();
        spritesheetScrollpane.setContent(openedSpritesheet);
    }

    public Stage createOpenSpritesheetWindow(MouseEvent event) {
        Stage popupWindow = createPopup("Open Spritesheet", 600, 480, event);
        Button openFileSearchButton = new Button("Select Spritesheet");
        Button setSpritesheetButton = new Button("Set Spritesheet");
        Text openFileResultMessage = new Text();
        setSpritesheetButton.setManaged(false);
        setSpritesheetButton.setVisible(false);
        Pane popupContent = (Pane) popupWindow.getScene().getRoot();
        VBox vbox = new VBox();
        vbox.setPrefWidth(popupContent.getWidth());
        ImageView previewImage = new ImageView();
        vbox.getChildren().add(openFileSearchButton);
        vbox.getChildren().add(openFileResultMessage);
        vbox.getChildren().add(previewImage);
        vbox.getChildren().add(setSpritesheetButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Spritesheet");
        popupContent.getChildren().add(vbox);
        openFileSearchButton.setOnMouseClicked(e -> {
            // Show the file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(popupWindow);
            previewImage.setImage(null);
            if (selectedFile != null) {
                // Check the file type based on its extension or MIME type
                String fileName = selectedFile.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                if ("jpg".equalsIgnoreCase(fileExtension) ||
                        "jpeg".equalsIgnoreCase(fileExtension) ||
                        "png".equalsIgnoreCase(fileExtension)) {
                    // Selected file is an image
                    previewImage.setImage(new Image(selectedFile.toURI().toString()));
                    previewImage.setPreserveRatio(true);
                    previewImage.setFitWidth(200);
                    openFileResultMessage.setText("Opened Spritesheet: " + selectedFile.getName());
                    setSpritesheetButton.setVisible(true);
                    setSpritesheetButton.setManaged(true);
                } else {
                    previewImage.setImage(null);
                    openFileResultMessage.setText("Invalid file selected. Please select an image.");
                    setSpritesheetButton.setVisible(false);
                    setSpritesheetButton.setManaged(false);
                }
            }
        });
        setSpritesheetButton.setOnMouseClicked(e -> {
            if (previewImage.getImage() != null) {
                openedSpritesheet.setImage(previewImage.getImage());
                popupWindow.close();
            }
        });

        return popupWindow;
    }

    public void openSpritesheet(MouseEvent event) {
        createOpenSpritesheetWindow(event).show();
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
