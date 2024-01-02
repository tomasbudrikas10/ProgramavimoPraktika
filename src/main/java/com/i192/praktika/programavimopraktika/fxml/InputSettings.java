package com.i192.praktika.programavimopraktika.fxml;


import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import com.i192.praktika.programavimopraktika.controller.ControllerMapingsReadWriter;
import com.i192.praktika.programavimopraktika.controller.Input;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.io.IOException;
import java.util.HashMap;

public class InputSettings {

    public Text textInfo;
    static class ControllerSetupContext{
        public Controller controller;
        public HashMap<Input, Component> inputHashMap;
        int i = 0;

        public ControllerSetupContext(){
            this.controller = null;
            this.inputHashMap = new HashMap<>();
        }
    }

    public void doneAction() throws IOException {
        SceneManager.getInstance().setScene("main menu");
    }

    public void ControllerSetupLoop(){
        //this should be called when a button is pressed named something like: setup controller
        //first ask to press any button on controller, so we know what controller is getting set up
        //then ask to press the button the player would like to have bind as punch, then kick....
        //then once all these are set up save it to a file, so this step won't be repeated each time
        //PYRST



        //I will create a hash map of componet(controllers has many components) to input(enum I created for each command used in game)

        AnimationTimer timer = new AnimationTimer() {
            ControllerSetupContext csc = new ControllerSetupContext();
            @Override
            public void handle(long now) {
                if(csc.controller == null){
                    textInfo.textProperty().set("Press any button on your controller.");
                    csc.controller = ControllerManager.firstControllerWithComponentOn(ControllerManager.getAllUsableControllers());
                }
                else {
                    if(csc.i == Input.values().length){
                        //if all inputs were mapped
                        textInfo.textProperty().set("");
                        ControllerMapingsReadWriter.writeControllerMappings(csc.inputHashMap, csc.controller);
                        this.stop();
                    }else if(!csc.inputHashMap.containsKey(Input.values()[csc.i])){
                        Component comp = ControllerManager.getComponentOn(csc.controller);
                        textInfo.textProperty().set("Press button for:" + Input.DOWN.getInputName(Input.values()[csc.i]) + ".");
                        if(comp != null){
                            csc.inputHashMap.put(Input.values()[csc.i], comp);
                            csc.i = csc.i + 1;
                        }
                    }
                }
            }

        };
        timer.start();
    }



}
