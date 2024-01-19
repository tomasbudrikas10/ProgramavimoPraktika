package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.Characters;
import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.game.Fighter;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CharacterSelect implements Initialisable{

    @FXML
    public GridPane charactersGrid;

    //toDo: pass in the controllers from the previous scene...
    //toDo: have some characters to select...
    public ConfiguredController playerA = null;
    public ConfiguredController playerB = null;
    public Text turnText;
    public ImageView player1icon;
    public ImageView player2icon;
    int selectedRowIndex = 0;
    int selectedColumnIndex = 0;
    boolean playerOneTurn;
    boolean bothSelected;
    ArrayList<StackPane> characters;
    ArrayList<String> characterIconStrings;
    ArrayList<Pair<Integer, Integer>> selectedCharacters;


    public void setPlayers(ConfiguredController playerA , ConfiguredController playerB){
        this.playerA = playerA;
        this.playerB = playerB;
        this.characters = new ArrayList<>();
        this.selectedCharacters = new ArrayList<>();
        this.characterIconStrings = new ArrayList<>();
        this.bothSelected = false;
    }

    public void charSelectLoop(){

        boolean characters_selected = false;
        if (!charactersGrid.getChildren().isEmpty()) {
            markCurrentlySelectedCharacter();
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                playerA.updateLatestChanges();
                playerB.updateLatestChanges();
                if (!bothSelected) {
                    if (playerOneTurn) {
                        handleInputs(playerA);
                    } else {
                        handleInputs(playerB);
                    }
                } else {
                    this.stop();
                }
            }
        };
        timer.start();
    }

    private void handleInputs(ConfiguredController playerB) {
        for (Pair<Inputs, Boolean> pair : playerB.latestChanges) {
            int oldColIndex = selectedColumnIndex;
            int oldRowIndex = selectedRowIndex;
            if (pair.getKey() == Inputs.LEFT && pair.getValue()) {
                if (selectedColumnIndex > 0) {
                    selectedColumnIndex--;
                }
            } else if (pair.getKey() == Inputs.RIGHT && pair.getValue()) {
                if (selectedColumnIndex < charactersGrid.getColumnCount()-1) {
                    if (selectedRowIndex * charactersGrid.getRowCount() + selectedColumnIndex + 1 < charactersGrid.getChildren().size()) {
                        selectedColumnIndex++;
                    }
                }
            } else if (pair.getKey() == Inputs.UP && pair.getValue()) {
                if (selectedRowIndex > 0) {
                    selectedRowIndex--;
                }
            } else if (pair.getKey() == Inputs.DOWN && pair.getValue()) {
                if (selectedRowIndex < charactersGrid.getRowCount()-1) {
                    if (selectedRowIndex * charactersGrid.getRowCount() + selectedColumnIndex + charactersGrid.getRowCount() < charactersGrid.getChildren().size()) {
                        selectedRowIndex++;
                    }
                }
            } else if (pair.getKey() == Inputs.PUNCH && pair.getValue()) {
                confirmCurrentlySelectedCharacter();
            }
            if (oldColIndex != selectedColumnIndex || oldRowIndex != selectedRowIndex) {
                unmarkSelectedCharacter(oldRowIndex, oldColIndex);
                markCurrentlySelectedCharacter();
            }
        }
    }

    public void markCurrentlySelectedCharacter() {
        if (!bothSelected) {
            StackPane currentlySelectedCharacter = (StackPane) charactersGrid.getChildren().get(selectedRowIndex * charactersGrid.getRowCount() + selectedColumnIndex);
            Color borderColor;
            if (playerOneTurn) {
                borderColor = Color.RED;
            } else {
                borderColor = Color.BLUE;
            }
            currentlySelectedCharacter.setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        } else {
            unmarkSelectedCharacter(selectedRowIndex, selectedColumnIndex);
        }
    }

    public void confirmCurrentlySelectedCharacter() {
        if (!bothSelected) {
            StackPane currentlySelectedCharacter = (StackPane) charactersGrid.getChildren().get(selectedRowIndex * charactersGrid.getRowCount() + selectedColumnIndex);
            for (Pair<Integer, Integer> selectedCharacter : selectedCharacters) {
                if (selectedCharacter.getKey() == selectedRowIndex && selectedCharacter.getValue() == selectedColumnIndex) {
                    return;
                }
            }
            selectedCharacters.add(new Pair<Integer, Integer>(selectedRowIndex, selectedColumnIndex));
            Color borderColor;
            Color fillColor;

            if (playerOneTurn) {
                borderColor = Color.RED;
                fillColor = Color.rgb(184, 108, 108, 0.9);
                setPlayerIcon(selectedRowIndex * charactersGrid.getRowCount() + selectedColumnIndex);
                playerOneTurn = false;
                updateTurnText();
            } else {
                borderColor = Color.BLUE;
                fillColor = Color.rgb(108, 123, 184, 0.9);
                setPlayerIcon(selectedRowIndex * charactersGrid.getRowCount() + selectedColumnIndex);
                bothSelected = true;
                updateTurnText();
            }
            currentlySelectedCharacter.setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
            VBox vbox = (VBox) currentlySelectedCharacter.getChildren().get(1);
            vbox.setBackground(Background.fill(fillColor));
            vbox.setOpacity(1);
            markCurrentlySelectedCharacter();
            if (bothSelected) {
                startGame();
            }
        }
    }
    public void unmarkSelectedCharacter(int x, int y) {
        StackPane currentlySelectedCharacter = (StackPane) charactersGrid.getChildren().get(x * charactersGrid.getRowCount() + y);
        currentlySelectedCharacter.setBorder(null);
    }

    public void setPlayerIcon(int index) {
        if (playerOneTurn) {
            player1icon.setImage(new Image(characterIconStrings.get(index)));
        } else {
            player2icon.setImage(new Image(characterIconStrings.get(index)));
        }
    }

    @Override
    public void initialise() {
        playerOneTurn = true;
        updateTurnText();
        player2icon.setScaleX(-1);
        int characterCount = 0;
        for (Characters character : Characters.values()) {
            StackPane stackPane = new StackPane();
            Pane pane = new Pane();
            pane.setBackground(Background.fill(new ImagePattern(new Image( getClass().getResource("/com/i192/praktika/programavimopraktika/images/" + character.getImageName()).toExternalForm()))));
            stackPane.getChildren().add(pane);
            VBox vbox = new VBox();
            vbox.setBackground(Background.fill(Color.rgb(128, 128, 128, 0.7)));
            Text name = new Text(character.getName());
            Text description = new Text(character.getDescription());
            vbox.getChildren().addAll(name, description);
            name.setFont(Font.font(28));
            name.setFill(Paint.valueOf("white"));
            description.setFill(Paint.valueOf("white"));
            description.setFont(Font.font(16));
            vbox.setSpacing(15);
            vbox.setAlignment(Pos.CENTER);
            vbox.setOpacity(0);
            stackPane.getChildren().add(vbox);
            charactersGrid.add(stackPane, characterCount % charactersGrid.getColumnCount(), characterCount / (charactersGrid.getColumnCount()));
            characterIconStrings.add(getClass().getResource("/com/i192/praktika/programavimopraktika/images/" + character.getImageName()).toExternalForm());
            characters.add(stackPane);
            characterCount++;
        }
        charSelectLoop();
    }
    public void updateTurnText() {
        if (!bothSelected) {
            if (playerOneTurn) {
                turnText.setText("Player 1 turn to pick a fighter.");
            } else {
                turnText.setText("Player 2 turn to pick a fighter.");
            }
        }
    }

    public void startGame() {
        Duration duration = Duration.seconds(1);
        int totalSeconds = 0;
        AtomicInteger secondsRemaining = new AtomicInteger(10);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(duration, event -> {
            turnText.setText("Game starting in " + secondsRemaining + " seconds.");
            secondsRemaining.getAndDecrement();
        }));
        timeline.setCycleCount(totalSeconds);
        timeline.setOnFinished(e2 -> {
            try {

                OneVSOneFight ovof = SceneManager.getInstance().getLoader(Scenes.ONE_VS_ONE_FIGHT).getController();
                Characters a = Characters.values()[selectedCharacters.get(0).getKey() * charactersGrid.getColumnCount() + selectedCharacters.get(0).getValue()];
                Characters b = Characters.values()[selectedCharacters.get(1).getKey() * charactersGrid.getColumnCount() + selectedCharacters.get(1).getValue()];
                a.getFighter().character = a;
                b.getFighter().character = b;
                ovof.setSelectedCharacters(a.getFighter(), b.getFighter());
                ovof.setPlayers(playerA, playerB);

                SceneManager.getInstance().setScene(Scenes.ONE_VS_ONE_FIGHT);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        timeline.play();
    }
}
