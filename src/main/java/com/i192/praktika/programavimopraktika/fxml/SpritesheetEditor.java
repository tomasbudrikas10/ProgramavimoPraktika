package com.i192.praktika.programavimopraktika.fxml;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class SpritesheetEditor implements Initialisable {
    public ImageView openedSpritesheet;
    public int openedSpritesheetRowCount;
    public int openedSpritesheetColCount;
    public Integer currentlyDisplayedRow;
    public Integer currentlyDisplayedCol;
    public ImageView currentlyDisplayed;
    public ScrollPane spritesheetScrollpane;
    public ArrayList<ArrayList<Image>> frames;
    public Text currentlyDisplayedColLabel;
    public Text currentlyDisplayedRowLabel;
    public Text openedSpritesheetRowCountLabel;
    public Text openedSpritesheetColCountLabel;

    @Override
    public void initialise() {
        openedSpritesheet = new ImageView();
        currentlyDisplayed = new ImageView();
        openedSpritesheetRowCount = 0;
        openedSpritesheetColCount = 0;
        currentlyDisplayedRow = 0;
        currentlyDisplayedCol = 0;
        currentlyDisplayedRowLabel.setVisible(false);
        currentlyDisplayedColLabel.setVisible(false);
        frames = new ArrayList<>();
        spritesheetScrollpane.setContent(currentlyDisplayed);
    }

    public Stage createOpenSpritesheetWindow(MouseEvent event) {
        Stage popupWindow = createPopup("Open Spritesheet", 600, 480, event);
        Button openFileSearchButton = new Button("Select Spritesheet");
        Button setSpritesheetButton = new Button("Set Spritesheet");
        Text openFileResultMessage = new Text();
        Text rowCountLabel = new Text("Enter row count:");
        Text colCountLabel = new Text("Enter column count:");
        Spinner<Integer> rowCountInput = new Spinner<>();
        Spinner<Integer> colCountInput = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        rowCountInput.setValueFactory(valueFactory1);
        colCountInput.setValueFactory(valueFactory2);
        setSpritesheetButton.setManaged(false);
        setSpritesheetButton.setVisible(false);
        rowCountInput.setVisible(false);
        rowCountInput.setManaged(false);
        colCountInput.setVisible(false);
        colCountInput.setManaged(false);
        rowCountLabel.setVisible(false);
        rowCountLabel.setManaged(false);
        colCountLabel.setVisible(false);
        colCountLabel.setManaged(false);
        Pane popupContent = (Pane) popupWindow.getScene().getRoot();
        VBox vbox = new VBox();
        vbox.setPrefWidth(popupContent.getWidth());
        ImageView previewImage = new ImageView();
        vbox.getChildren().addAll(openFileSearchButton, openFileResultMessage, previewImage, rowCountLabel, rowCountInput, colCountLabel, colCountInput, setSpritesheetButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Spritesheet");
        popupContent.getChildren().add(vbox);
        openFileSearchButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser.showOpenDialog(popupWindow);
            previewImage.setImage(null);
            if (selectedFile != null) {
                String fileName = selectedFile.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                if ("jpg".equalsIgnoreCase(fileExtension) ||
                        "jpeg".equalsIgnoreCase(fileExtension) ||
                        "png".equalsIgnoreCase(fileExtension)) {
                    previewImage.setImage(new Image(selectedFile.toURI().toString()));
                    previewImage.setPreserveRatio(true);
                    previewImage.setFitWidth(200);
                    openFileResultMessage.setText("Opened Spritesheet: " + selectedFile.getName());
                    setSpritesheetButton.setVisible(true);
                    setSpritesheetButton.setManaged(true);
                    rowCountInput.setVisible(true);
                    rowCountInput.setManaged(true);
                    colCountInput.setVisible(true);
                    colCountInput.setManaged(true);
                    rowCountLabel.setVisible(true);
                    rowCountLabel.setManaged(true);
                    colCountLabel.setVisible(true);
                    colCountLabel.setManaged(true);
                } else {
                    previewImage.setImage(null);
                    openFileResultMessage.setText("Invalid file selected. Please select an image.");
                    setSpritesheetButton.setVisible(false);
                    setSpritesheetButton.setManaged(false);
                    rowCountInput.setVisible(false);
                    rowCountInput.setManaged(false);
                    colCountInput.setVisible(false);
                    colCountInput.setManaged(false);
                    rowCountLabel.setVisible(false);
                    rowCountLabel.setManaged(false);
                    colCountLabel.setVisible(false);
                    colCountLabel.setManaged(false);
                }
            }
        });
        setSpritesheetButton.setOnMouseClicked(e -> {
            if (previewImage.getImage() != null) {
                openedSpritesheet.setImage(previewImage.getImage());
                openedSpritesheetRowCount = rowCountInput.getValue();
                openedSpritesheetColCount = colCountInput.getValue();
                currentlyDisplayedRow = 0;
                currentlyDisplayedCol = 0;
                currentlyDisplayedRowLabel.setVisible(true);
                currentlyDisplayedColLabel.setVisible(true);
                updateTotalRowColLabels();
                updateCurrentRowColLabels();
                setFrames(rowCountInput.getValue(), colCountInput.getValue());
                displayFrame(0, 0);
                popupWindow.close();
            }
        });

        return popupWindow;
    }

    public void openSpritesheet(MouseEvent event) {
        createOpenSpritesheetWindow(event).show();
    }

    public void setCurrentlyDisplayed(Image image) {
        currentlyDisplayed.setImage(image);
    }

    public void setFrames(int rowCount, int colCount) {
        frames.clear();
        Image spritesheetImage = openedSpritesheet.getImage();
        PixelReader pixelReader = spritesheetImage.getPixelReader();

        double spriteWidth = spritesheetImage.getWidth() / colCount;
        double spriteHeight = spritesheetImage.getHeight() / rowCount;

        for (int row = 0; row < rowCount; row++) {
            frames.add(new ArrayList<>());
            for (int col = 0; col < colCount; col++) {
                int x = (int) (col * spriteWidth);
                int y = (int) (row * spriteHeight);

                WritableImage spriteImage = new WritableImage(pixelReader, x, y, (int) spriteWidth, (int) spriteHeight);
                frames.get(row).add(spriteImage);
            }
        }
    }

    public void displayFrame(int rowIndex, int colIndex) {
        setCurrentlyDisplayed(frames.get(rowIndex).get(colIndex));
    }

    public void displayNextFrameOfRow() {
        if (currentlyDisplayedCol + 1 < openedSpritesheetColCount) {
            currentlyDisplayedCol++;
            displayFrame(currentlyDisplayedRow, currentlyDisplayedCol);
            updateCurrentRowColLabels();
        }
    }

    public void displayPreviousFrameOfRow() {
        if (currentlyDisplayedCol - 1 >= 0) {
            currentlyDisplayedCol--;
            displayFrame(currentlyDisplayedRow, currentlyDisplayedCol);
            updateCurrentRowColLabels();
        }
    }

    public void displayNextRow() {
        if (currentlyDisplayedRow + 1 < openedSpritesheetRowCount) {
            currentlyDisplayedRow++;
            currentlyDisplayedCol = 0;
            displayFrame(currentlyDisplayedRow, currentlyDisplayedCol);
            updateCurrentRowColLabels();
        }
    }


    public void displayPreviousRow() {
        if (currentlyDisplayedRow - 1 >= 0) {
            currentlyDisplayedRow--;
            currentlyDisplayedCol = 0;
            displayFrame(currentlyDisplayedRow, currentlyDisplayedCol);
            updateCurrentRowColLabels();
        }
    }
    public void updateCurrentRowColLabels() {
        currentlyDisplayedRowLabel.setText(String.valueOf(currentlyDisplayedRow + 1));
        currentlyDisplayedColLabel.setText(String.valueOf(currentlyDisplayedCol + 1));
    }

    public void updateTotalRowColLabels() {
        openedSpritesheetRowCountLabel.setText(String.valueOf(openedSpritesheetRowCount));
        openedSpritesheetColCountLabel.setText(String.valueOf(openedSpritesheetColCount));
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
