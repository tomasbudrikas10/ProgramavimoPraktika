package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.spritesheet.BoxTypes;
import com.i192.praktika.programavimopraktika.spritesheet.HitHurtCollisionBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SpritesheetEditor implements Initialisable {

    private double startX, startY, endX, endY;
    public ImageView openedSpritesheet;
    public int openedSpritesheetRowCount;
    public int openedSpritesheetColCount;
    public ArrayList<ArrayList<ArrayList<HitHurtCollisionBox>>> boxes;
    public ArrayList<ArrayList<Pair<Double, Double>>> frameVelocities;
    public Integer currentlyDisplayedRow;
    public Integer currentlyDisplayedCol;
    public ImageView currentlyDisplayed;
    public ScrollPane spritesheetScrollpane;
    public ArrayList<ArrayList<Image>> frames;
    public Text currentlyDisplayedColLabel;
    public Text currentlyDisplayedRowLabel;
    public Text openedSpritesheetRowCountLabel;
    public Text openedSpritesheetColCountLabel;
    public StackPane boxStackPane;
    public VBox vboxOfXVelocityAmountSpinner;
    public Text xVelocityAmountLabel;
    public VBox vboxOfYVelocityAmountSpinner;
    public Text yVelocityAmountLabel;
    public BufferedReader frameData;
    Spinner<Integer> translateAmountSpinner;
    Spinner<Double> xVelocityAmountSpinner;
    Spinner<Double> yVelocityAmountSpinner;
    public ListView<Text> frameBoxesListView;
    public Integer selectedBoxId;
    public Text translationAmountLabel;
    public VBox vboxOfTranslationAmountSpinner;

    public Button cycleBoxTypeButton;
    public int currentlyAddingBoxType = 0;

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
        selectedBoxId = null;
        frames = new ArrayList<>();
        frameVelocities = new ArrayList<>();
        boxes = new ArrayList<>();
        boxStackPane.setAlignment(Pos.TOP_LEFT);
        spritesheetScrollpane.setContent(currentlyDisplayed);
        translateAmountSpinner = new Spinner<>(1, 1000, 10);
        translationAmountLabel.setText("10");
        translateAmountSpinner.getValueFactory().valueProperty().addListener((obs, oldVal, newVal) -> {
            translationAmountLabel.setText(String.valueOf(newVal));
        });
        xVelocityAmountSpinner = new Spinner<>(0.0, 1000.0, 0.0);
        xVelocityAmountLabel.setText("0");
        xVelocityAmountSpinner.getValueFactory().valueProperty().addListener((obs, oldVal, newVal) -> {
            xVelocityAmountLabel.setText(String.valueOf(newVal));
            frameVelocities.get(currentlyDisplayedRow).set(currentlyDisplayedCol, new Pair<>(newVal, frameVelocities.get(currentlyDisplayedRow).get(currentlyDisplayedCol).getValue()));
        });
        yVelocityAmountSpinner = new Spinner<>(0.0, 1000.0, 0.0);
        yVelocityAmountLabel.setText("0");
        yVelocityAmountSpinner.getValueFactory().valueProperty().addListener((obs, oldVal, newVal) -> {
            yVelocityAmountLabel.setText(String.valueOf(newVal));
            frameVelocities.get(currentlyDisplayedRow).set(currentlyDisplayedCol, new Pair<>(frameVelocities.get(currentlyDisplayedRow).get(currentlyDisplayedCol).getKey(), newVal));
        });
        vboxOfTranslationAmountSpinner.getChildren().add(translateAmountSpinner);
        vboxOfXVelocityAmountSpinner.getChildren().add(xVelocityAmountSpinner);
        vboxOfYVelocityAmountSpinner.getChildren().add(yVelocityAmountSpinner);
    }

    public Stage createOpenSpritesheetWindow(MouseEvent event) {
        Stage popupWindow = createPopup("Open Spritesheet", 600, 480, event);
        Button openFileSearchButton = new Button("Select Spritesheet");
        Button openFrameDataFileSearchButton = new Button("Load Framedata");
        Button setSpritesheetButton = new Button("Set Spritesheet");
        Text openFileResultMessage = new Text();
        Text openFrameDataResultMessage = new Text();
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
        openFrameDataFileSearchButton.setVisible(false);
        openFrameDataFileSearchButton.setManaged(false);
        rowCountInput.setEditable(true);
        colCountInput.setEditable(true);
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
        vbox.getChildren().addAll(openFileSearchButton, openFileResultMessage, openFrameDataFileSearchButton, openFrameDataResultMessage, previewImage, rowCountLabel, rowCountInput, colCountLabel, colCountInput, setSpritesheetButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("Select Spritesheet");
        fileChooser1.setInitialDirectory(new File("src/main/resources/com/i192/praktika/programavimopraktika/animation/sprite_sheets") );


        FileChooser fileChooser2 = new FileChooser();
        fileChooser2.setTitle("Select Frame Data");
        fileChooser2.setInitialDirectory(new File("src/main/resources/com/i192/praktika/programavimopraktika/animation/frame_datas"));

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser2.getExtensionFilters().add(extFilter);
        popupContent.getChildren().add(vbox);
        openFileSearchButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser1.showOpenDialog(popupWindow);
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
                    openFrameDataFileSearchButton.setVisible(true);
                    openFrameDataFileSearchButton.setManaged(true);
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
                    openFrameDataResultMessage.setText("");
                    frameData = null;
                    setSpritesheetButton.setVisible(false);
                    setSpritesheetButton.setManaged(false);
                    openFrameDataFileSearchButton.setVisible(false);
                    openFrameDataFileSearchButton.setManaged(false);
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

        openFrameDataFileSearchButton.setOnMouseClicked(e -> {
                File selectedFile = fileChooser2.showOpenDialog(popupWindow);
                if (selectedFile != null) {
                    String fileName = selectedFile.getName();
                    openFrameDataResultMessage.setText("Opened Frame Data: " + fileName);
                    try {
                        frameData = new BufferedReader(new FileReader(selectedFile));
                        rowCountInput.setManaged(false);
                        rowCountInput.setVisible(false);
                        colCountInput.setManaged(false);
                        colCountInput.setVisible(false);
                        rowCountLabel.setManaged(false);
                        rowCountLabel.setVisible(false);
                        colCountLabel.setManaged(false);
                        colCountLabel.setVisible(false);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    openFrameDataResultMessage.setText("Invalid file selected.");
                    if (frameData != null) {
                        try {
                            frameData.close();
                            rowCountInput.setManaged(true);
                            rowCountInput.setVisible(true);
                            colCountInput.setManaged(true);
                            colCountInput.setVisible(true);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frameData = null;
                    }
                }
        });

        setSpritesheetButton.setOnMouseClicked(e -> {
            if (previewImage.getImage() != null) {
                openedSpritesheet.setImage(previewImage.getImage());
                currentlyDisplayedRow = 0;
                currentlyDisplayedCol = 0;
                currentlyDisplayedRowLabel.setVisible(true);
                currentlyDisplayedColLabel.setVisible(true);
                if (frameData != null) {
                    try {
                        loadFrameData();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    openedSpritesheetRowCount = rowCountInput.getValue();
                    openedSpritesheetColCount = colCountInput.getValue();
                    setFrames(openedSpritesheetRowCount, openedSpritesheetColCount);
                }
                updateTotalRowColLabels();
                updateCurrentRowColLabels();
                displayFrame(0, 0);
                popupWindow.close();
            }
        });

        return popupWindow;
    }

    private void loadFrameData() throws IOException {
        String rows = frameData.readLine();
        String cols = frameData.readLine();
        ArrayList<String> translations = new ArrayList<>(Arrays.asList(frameData.readLine().split(" ")));
        ArrayList<String> velocities = new ArrayList<>(Arrays.asList(frameData.readLine().split(" ")));
        String currentBox = frameData.readLine();
        if (currentBox != null) {
            currentBox = currentBox.trim();
        }
        openedSpritesheetRowCount = Integer.parseInt(rows);
        openedSpritesheetColCount = Integer.parseInt(cols);
        setFrames(openedSpritesheetRowCount, openedSpritesheetColCount);
        for (int i = 0; i < Integer.parseInt(rows); i++) {
            for (int j = 0; j < Integer.parseInt(cols); j++) {
                String[] velocity = velocities.get(i*j + j).split(":");
                frameVelocities.get(i).set(j, new Pair<Double, Double>(Double.parseDouble(velocity[0]), Double.parseDouble(velocity[1])));
            }
        }
        while (currentBox != null) {
            String[] boxData = currentBox.split(" ");
            String boxTypeString = boxData[0];
            BoxTypes boxType = null;
            switch (boxTypeString) {
                case "hitBox":
                    boxType = BoxTypes.HIT_BOX;
                    break;
                case "hurtBox":
                    boxType = BoxTypes.HURT_BOX;
                    break;
                case "collisionBox":
                    boxType = BoxTypes.COLLISION_BOX;
                    break;
                default:
                    //boxType = BoxTypes.HIT_BOX;
                    break;
            }
            int boxRow = Integer.parseInt(boxData[1]);
            int boxCol = Integer.parseInt(boxData[2]);
            int boxWidth = Integer.parseInt(boxData[3]);
            int boxHeight = Integer.parseInt(boxData[4]);
            int boxXOffset = Integer.parseInt(boxData[5]);
            int boxYOffset = Integer.parseInt(boxData[6]);
            loadBox(boxType, boxRow, boxCol, boxWidth, boxHeight, boxXOffset, boxYOffset);
            currentBox = frameData.readLine();
            if (currentBox != null) {
                currentBox = currentBox.trim();
            }
        }
    }

    public void openSpritesheet(MouseEvent event) {
        createOpenSpritesheetWindow(event).show();
    }

    public void openAddBoxWindow(MouseEvent event) {
        createAddBoxWindow(event).show();
    }

    public void setCurrentlyDisplayed(Image image) {
        currentlyDisplayed.setImage(image);
    }

    public void setFrames(int rowCount, int colCount) {
        frames.clear();
        boxes.clear();
        frameVelocities.clear();
        Image spritesheetImage = openedSpritesheet.getImage();
        PixelReader pixelReader = spritesheetImage.getPixelReader();

        double spriteWidth = spritesheetImage.getWidth() / colCount;
        double spriteHeight = spritesheetImage.getHeight() / rowCount;

        for (int row = 0; row < rowCount; row++) {
            frames.add(new ArrayList<>());
            boxes.add(new ArrayList<>());
            frameVelocities.add(new ArrayList<>());
            for (int col = 0; col < colCount; col++) {
                boxes.get(row).add(new ArrayList<>());
                frameVelocities.get(row).add(new Pair<>(0.0, 0.0));
                int x = (int) (col * spriteWidth);
                int y = (int) (row * spriteHeight);

                WritableImage spriteImage = new WritableImage(pixelReader, x, y, (int) spriteWidth, (int) spriteHeight);
                frames.get(row).add(spriteImage);
            }
        }
    }

    public void updateVelocitySpinnerAndLabelValuesOnFrameChange() {
        xVelocityAmountSpinner.getValueFactory().setValue(frameVelocities.get(currentlyDisplayedRow).get(currentlyDisplayedCol).getKey());
        xVelocityAmountLabel.setText(String.valueOf(frameVelocities.get(currentlyDisplayedRow).get(currentlyDisplayedCol).getKey()));
        yVelocityAmountSpinner.getValueFactory().setValue(frameVelocities.get(currentlyDisplayedRow).get(currentlyDisplayedCol).getKey());
        yVelocityAmountLabel.setText(String.valueOf(frameVelocities.get(currentlyDisplayedRow).get(currentlyDisplayedCol).getKey()));
    }

    public void displayFrame(int rowIndex, int colIndex) {
        setCurrentlyDisplayed(frames.get(rowIndex).get(colIndex));
        selectedBoxId = null;
        displayCurrentBoxes();
        updateVelocitySpinnerAndLabelValuesOnFrameChange();
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
    public void displayCurrentBoxes() {
        if (!boxes.isEmpty()) {
            boxStackPane.getChildren().clear();
            ArrayList<HitHurtCollisionBox> boxesOfFrame = boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol);
            for (int i = 0; i < boxesOfFrame.size(); i++) {
                HitHurtCollisionBox box = boxesOfFrame.get(i);
                Rectangle rectangle = createRectangleFromBox(box);
                rectangle.setUserData(i);
                rectangle.setOnMouseClicked(e -> {
                    selectedBoxId = (int) rectangle.getUserData();
                    frameBoxesListView.getSelectionModel().selectIndices(selectedBoxId);
                    displayCurrentBoxes();
                });
                if (selectedBoxId != null && selectedBoxId == i) {
                    rectangle.setOpacity(1);
                    rectangle.setStroke(Color.CYAN);
                    rectangle.setStrokeWidth(2);
                }
                boxStackPane.getChildren().add(rectangle);
            }
            updateFrameBoxList();
        }
    }

    public void selectBoxFromList() {
        if (frameBoxesListView.getSelectionModel().getSelectedItem() != null &&
                frameBoxesListView.getSelectionModel().getSelectedItem().getUserData() != null) {
            selectedBoxId = (int) frameBoxesListView.getSelectionModel().getSelectedItem().getUserData();
        } else {
            selectedBoxId = null;
        }
        displayCurrentBoxes();
        updateFrameBoxList();
    }

    public void moveSelectedBoxRight() {
        if (selectedBoxId != null) {
            HitHurtCollisionBox box = boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol).get(selectedBoxId);
            box.setxOffset(box.getxOffset() + translateAmountSpinner.getValue());
        }
        displayCurrentBoxes();
    }

    public void moveSelectedBoxLeft() {
        if (selectedBoxId != null) {
            HitHurtCollisionBox box = boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol).get(selectedBoxId);
            box.setxOffset(box.getxOffset() - translateAmountSpinner.getValue());
        }
        displayCurrentBoxes();
    }

    public void moveSelectedBoxUp() {
        if (selectedBoxId != null) {
            HitHurtCollisionBox box = boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol).get(selectedBoxId);
            box.setyOffset(box.getyOffset() - translateAmountSpinner.getValue());
        }
        displayCurrentBoxes();
    }

    public void deleteSelectedBox() {
        if (selectedBoxId != null) {
            boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol).remove((int) selectedBoxId);
            selectedBoxId = null;
        }
        displayCurrentBoxes();
    }

    public void moveSelectedBoxDown() {
        if (selectedBoxId != null) {
            HitHurtCollisionBox box = boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol).get(selectedBoxId);
            box.setyOffset(box.getyOffset() + translateAmountSpinner.getValue());
        }
        displayCurrentBoxes();
    }

    public void updateFrameBoxList() {
        if (!boxes.isEmpty()) {
            ArrayList<HitHurtCollisionBox> arr = boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol);
            ArrayList<Text> labelList = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                HitHurtCollisionBox box = arr.get(i);
                Text text = new Text(box.getName() + " " + (i+1));
                text.setUserData(i);
                labelList.add(text);
            }
            frameBoxesListView.setItems(FXCollections.observableList(labelList));
            if (selectedBoxId != null) {
                frameBoxesListView.getSelectionModel().select(selectedBoxId);
            }
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

    public void saveSpreetsheetDataToFile(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/com/i192/praktika/programavimopraktika/animation/frame_datas"));
        fileChooser.setTitle("Save Frame Data to File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(openedSpritesheetRowCount));
            writer.newLine();
            writer.write(String.valueOf(openedSpritesheetColCount));
            writer.newLine();
            for (int i = 0; i < openedSpritesheetRowCount; i++) {
                for (int j = 0; j < openedSpritesheetColCount; j++) {
                    writer.write(String.valueOf(frameVelocities.get(i).get(j).getKey() + ":" + frameVelocities.get(i).get(j).getValue()));
                    if (!(j == openedSpritesheetColCount)) {
                        writer.write(" ");
                    }
                }
            }
            writer.newLine();
            for (int i = 0; i < openedSpritesheetRowCount; i++) {
                for (int j = 0; j < openedSpritesheetColCount; j++) {
                    writer.write(String.valueOf(frameVelocities.get(i).get(j).getKey() + ":" + frameVelocities.get(i).get(j).getValue()));
                    if (!(j == openedSpritesheetColCount)) {
                        writer.write(" ");
                    }
                }
            }
            writer.newLine();
            for (int i = 0; i < openedSpritesheetRowCount; i++) {
                for (int j = 0; j < openedSpritesheetColCount; j++) {
                    if (!boxes.get(i).get(j).isEmpty()) {
                        for (HitHurtCollisionBox box : boxes.get(i).get(j)) {
                            writer.write(String.valueOf(box.getName() + " " + i + " " + j + " " + box.getWidth() + " " + box.getHeight() + " " + box.getxOffset() + " " + box.getyOffset()));
                            writer.newLine();
                        }
                    }
                }
            }
            writer.close();
        }
    }

    public void addBox(BoxTypes type, int width, int height) {
        HitHurtCollisionBox box = new HitHurtCollisionBox(type, width, height);
        this.boxes.get(currentlyDisplayedRow).get(currentlyDisplayedCol).add(box);
        updateFrameBoxList();
    }


    public void loadBox(BoxTypes type, int row, int col, int width, int height, int xOffset, int yOffset) {
        HitHurtCollisionBox box = new HitHurtCollisionBox(type, width, height);
        box.setxOffset(xOffset);
        box.setyOffset(yOffset);
        this.boxes.get(row).get(col).add(box);
        updateFrameBoxList();
    }


    public Rectangle createRectangleFromBox(HitHurtCollisionBox box) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(box.getWidth());
        rectangle.setHeight(box.getHeight());
        rectangle.setTranslateX(box.getxOffset());
        rectangle.setTranslateY(box.getyOffset());
        rectangle.setFill(box.getColor());
        rectangle.setOpacity(0.5);
        return rectangle;
    }

    public Stage createAddBoxWindow(MouseEvent event) {
        Stage popup = createPopup("Add Box", 600, 400, event);
        VBox vbox = new VBox();
        Text widthInputLabel = new Text("Enter Width:");
        Text heightInputLabel = new Text("Enter Height:");
        Spinner<Integer> widthInput = new Spinner<>();
        Spinner<Integer> heightInput = new Spinner<>();
        widthInput.setEditable(true);
        heightInput.setEditable(true);
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        widthInput.setValueFactory(valueFactory1);
        heightInput.setValueFactory(valueFactory2);
        Button addHitBox = new Button("Add Hit Box");
        Button addHurtBox = new Button("Add Hurt Box");
        Button addCollisionBox = new Button("Add Collision Box");
        addHitBox.setOnMouseClicked(e -> {
            addBox(BoxTypes.HIT_BOX, widthInput.getValue(), heightInput.getValue());
            displayCurrentBoxes();
            popup.close();
        });
        addHurtBox.setOnMouseClicked(e -> {
            addBox(BoxTypes.HURT_BOX, widthInput.getValue(), heightInput.getValue());
            displayCurrentBoxes();
            popup.close();
        });
        addCollisionBox.setOnMouseClicked(e -> {
            addBox(BoxTypes.COLLISION_BOX, widthInput.getValue(), heightInput.getValue());
            displayCurrentBoxes();
            popup.close();
        });
        vbox.getChildren().addAll(widthInputLabel, widthInput, heightInputLabel, heightInput, addHitBox, addHurtBox, addCollisionBox);
        ((Pane) popup.getScene().getRoot()).getChildren().add(vbox);
        return popup;
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
