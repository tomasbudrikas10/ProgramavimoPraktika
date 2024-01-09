package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.game.BasicCharacter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameStage implements Initialisable {
    @FXML
    public Pane game;
    @FXML
    public Pane background;
    @FXML
    public Rectangle ground;
    @FXML
    public Rectangle leftWall;
    @FXML
    public Rectangle rightWall;
    @FXML
    public StackPane root;
    @FXML
    public Rectangle playerOneSpawn;
    @FXML
    public Rectangle playerTwoSpawn;

    @Override
    public void initialise() {
        System.out.println(playerOneSpawn.getWidth());
            BasicCharacter playerOne = new BasicCharacter(playerOneSpawn.getLayoutX(), playerOneSpawn.getLayoutY(), 200, 300, new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/player1.png").toExternalForm()));
            BasicCharacter playerTwo = new BasicCharacter(playerTwoSpawn.getLayoutX() - 200, playerTwoSpawn.getLayoutY(), 200, 300, new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/player2.png").toExternalForm()));
            game.getChildren().addAll(playerOne, playerTwo);
            root.requestFocus();
            root.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.A) {
                    double newX = playerOne.getLayoutX() - 15;
                    playerOne.setLayoutX(newX);
                    if (playerOne.getBoundsInParent().intersects(playerTwo.getBoundsInParent())) {
                        double overlapX = playerTwo.getBoundsInParent().getMaxX() - playerOne.getBoundsInParent().getMinX();
                        playerOne.setLayoutX(playerOne.getLayoutX() + overlapX);
                    } else {
                        playerOne.setLayoutX(newX);
                    }
                }
                if (e.getCode() == KeyCode.D) {
                    double newX = playerOne.getLayoutX() + 15;
                    playerOne.setLayoutX(newX);
                    if (playerOne.getBoundsInParent().intersects(playerTwo.getBoundsInParent())) {
                        double overlapX = playerOne.getBoundsInParent().getMaxX() - playerTwo.getBoundsInParent().getMinX();
                        playerOne.setLayoutX(playerOne.getLayoutX() - overlapX);
                    } else {
                        playerOne.setLayoutX(newX);
                    }
                }
                if (e.getCode() == KeyCode.J) {
                    double newX = playerTwo.getLayoutX() - 15;
                    playerTwo.setLayoutX(newX);
                    if (playerTwo.getBoundsInParent().intersects(playerOne.getBoundsInParent())) {
                        double overlapX = playerOne.getBoundsInParent().getMaxX() - playerTwo.getBoundsInParent().getMinX();
                        playerTwo.setLayoutX(playerTwo.getLayoutX() + overlapX);
                    }
                }
                if (e.getCode() == KeyCode.L) {
                    double newX = playerTwo.getLayoutX() + 15;
                    playerTwo.setLayoutX(newX);
                    if (playerTwo.getBoundsInParent().intersects(playerOne.getBoundsInParent())) {
                        double overlapX = playerTwo.getBoundsInParent().getMaxX() - playerOne.getBoundsInParent().getMinX();
                        playerTwo.setLayoutX(playerTwo.getLayoutX() - overlapX);
                    } else {
                        playerTwo.setLayoutX(newX);
                    }
                }
            });
    }
}
