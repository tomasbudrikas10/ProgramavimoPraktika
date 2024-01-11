package com.i192.praktika.programavimopraktika.fxml;// Import statements...

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PlayersJoinBricked implements Initialisable {

    public VBox player;

    public void joinLoop() {
        AnimationTimer timer = new AnimationTimer() {
            final List<ConfiguredController> configuredControllers = ControllerManager.getConfiguredControllers();

            @Override
            public void handle(long now) {
                for (ConfiguredController c : configuredControllers) {
                    c.updateLatestChanges();
                }

                for (ConfiguredController c : configuredControllers) {
                    if (c.latestChanges.length != 0) {
                        Label label = new Label("Player " + c.getPlayerName() + " joined!");

                        String imagePath = "src/main/resources/com/i192/praktika/programavimopraktika/images/PlayerB.png";

                        Image playerImage = null;
                        try {
                            playerImage = new Image(new FileInputStream(imagePath));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException("src/main/resources/com/i192/praktika/programavimopraktika/images/playerA.png", e);
                        }

                        player.getChildren().addAll(label, new ImageView(playerImage));

                        if (configuredControllers.size() >= 2) {
                            try {
                                CharacterSelect cs = SceneManager.getInstance().getLoader(Scenes.CHARACTER_SELECT).getController();
                                cs.setPlayers(configuredControllers.get(0), configuredControllers.get(1));
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to load CharacterSelect", e);
                            }

                            try {
                                playersJoinedAction();
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to execute playersJoinedAction", e);

                            }
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
        joinLoop();
    }
}
