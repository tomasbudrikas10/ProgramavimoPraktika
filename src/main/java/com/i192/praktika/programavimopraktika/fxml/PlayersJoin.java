package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayersJoin implements Initialisable{

    public Label playerTurnLabel;
    public ImageView playerOneControllerImageView;
    public Label playerOneControllerType;
    public ImageView playerTwoControllerImageView;
    public Label playerTwoControllerType;
    public VBox root;

    public void joinLoop(){
        AnimationTimer timer = new AnimationTimer() {

            ArrayList<ConfiguredController> configuredControllers = ControllerManager.getConfiguredControllers();

            //toDo:display each configuredController so everyone can see

            ConfiguredController playerA = null;
            ConfiguredController playerB = null;

            @Override
            public void handle(long now) {

                for(ConfiguredController c:configuredControllers){
                    c.updateLatestChanges();
                }


                for(ConfiguredController c:configuredControllers) {
                    if (c.latestChanges.length != 0) {
                        if (playerA == null) {

                            playerA = c;

                            if (playerA.controller.getType() == Controller.Type.MOUSE) {
                                playerOneControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/mouse.png").toExternalForm()));

                            } else if (playerA.controller.getType() == Controller.Type.STICK || playerA.controller.getType() == Controller.Type.GAMEPAD) {
                                playerOneControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/console.png").toExternalForm()));

                            } else if (playerA.controller.getType() == Controller.Type.KEYBOARD){
                                playerOneControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/keyboard.png").toExternalForm()));
                            } else {
                                playerOneControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/surprise-box.png").toExternalForm()));
                            }
                            playerOneControllerType.setText("Player 1 has selected a: " + c.controller.getType());
                            playerTurnLabel.setText("Player 2's turn to select a controller!");
                            playerTwoControllerType.setText("Waiting for Player 2...");
                        } else if (playerB == null) {

                            if (c != playerA) {
                                playerB = c;

                                if (playerB.controller.getType() == Controller.Type.MOUSE) {
                                    playerTwoControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/mouse.png").toExternalForm()));

                                } else if (playerB.controller.getType() == Controller.Type.STICK || playerB.controller.getType() == Controller.Type.GAMEPAD) {
                                    playerTwoControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/console.png").toExternalForm()));

                                } else if (playerB.controller.getType() == Controller.Type.KEYBOARD){
                                    playerTwoControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/keyboard.png").toExternalForm()));
                                } else {
                                    playerTwoControllerImageView.setImage(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/surprise-box.png").toExternalForm()));
                                }
                                playerTwoControllerType.setText("Player 2 has selected a: " + c.controller.getType());
                                playerTurnLabel.setText("Heading to character selection...");
                            }
                        } else {
                            //send configured controllers to next scene controller
                            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
                            pauseTransition.setOnFinished(e -> {
                                CharacterSelect cs = null;
                                try {
                                    cs = SceneManager.getInstance().getLoader(Scenes.CHARACTER_SELECT).getController();
                                    cs.setPlayers(playerA, playerB);
                                    playersJoinedAction();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                            pauseTransition.play();
                            this.stop();
                        }
                    }
                }
            }
        };

        timer.start();
    }


    public void playersJoinedAction() throws IOException {
        SceneManager.getInstance().setScene(Scenes.CHARACTER_SELECT);
    }

    @Override
    public void initialise() {
        Image unchosen = new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/question-mark.png").toExternalForm());
        playerOneControllerImageView.setImage(unchosen);
        playerTwoControllerImageView.setImage(unchosen);
        playerOneControllerImageView.setFitWidth(300);
        playerOneControllerImageView.setFitHeight(300);
        playerTwoControllerImageView.setFitWidth(300);
        playerTwoControllerImageView.setFitHeight(300);
        joinLoop();
    }
}
