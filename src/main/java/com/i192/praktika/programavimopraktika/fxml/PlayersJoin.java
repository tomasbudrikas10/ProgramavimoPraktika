package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayersJoin implements Initialisable{

    public VBox player;

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

                            Label label1 = new Label("Player A joined!");
                            try {
                                Image image1 = new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/playerA.png"));
                                player.getChildren().add(label1);
                                player.getChildren().add(new ImageView(image1) );
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            playerA = c;
                        } else if (playerB == null) {

                            if (c != playerA) {

                                Label label2 = new Label("Player B joined!");
                                Image image2 =null;
                                try {
                                     image2 = new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/playerB.png"));

                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                player.getChildren().addAll(label2,new ImageView(image2));
                                playerB = c;
                            }
                        } else {
                            //send configured controllers to next scene controller
                            try {
                                CharacterSelect cs = SceneManager.getInstance().getLoader(Scenes.CHARACTER_SELECT).getController();
                                cs.setPlayers(playerA, playerB);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                playersJoinedAction();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
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
