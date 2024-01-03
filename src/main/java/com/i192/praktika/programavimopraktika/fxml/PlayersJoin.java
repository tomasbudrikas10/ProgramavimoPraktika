package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import javafx.animation.AnimationTimer;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayersJoin implements Initialisable{

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

                for(ConfiguredController c:configuredControllers){
                    if(c.latestChanges.length != 0) {
                        if(playerA == null){
                            //toDo:somehow show that playerA has joined
                            playerA = c;
                        }else if(playerB == null){

                            if(c != playerA){
                                //toDo:somehow show that playerB has joined
                                playerB = c;
                            }
                        }else {
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
