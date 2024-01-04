package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import javafx.animation.AnimationTimer;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterSelect implements Initialisable{

    //toDo: pass in the controllers from the previous scene...
    //toDo: have some characters to select...
    public ConfiguredController playerA = null;
    public ConfiguredController playerB = null;

    public void setPlayers(ConfiguredController playerA , ConfiguredController playerB){
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public void charSelectLoop(){

        boolean characters_selected = false;

        AnimationTimer timer = new AnimationTimer() {


            //toDo:display each joined player



            @Override
            public void handle(long now) {

                playerA.updateLatestChanges();
                playerB.updateLatestChanges();

                //toDO: respond to changed inputs


                if(characters_selected){
                    try {
                        OneVSOneFight ovof = SceneManager.getInstance().getLoader(Scenes.CHARACTER_SELECT).getController();
                        ovof.setPlayers(playerA, playerB);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        charactersSelectedAction();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    this.stop();
                }


            }

        };

        timer.start();
    }

    public void charactersSelectedAction() throws IOException {
        //toDo: make the 1v1 game scene
        //SceneManager.getInstance().setScene();
    }

    @Override
    public void initialise() {
        charSelectLoop();
    }
}
