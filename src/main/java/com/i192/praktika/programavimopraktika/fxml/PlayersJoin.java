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

public class PlayersJoin {
    ConfiguredController left;
    ConfiguredController right;

    public void JoinLoop(){
        //first id like the availiable controllers to be registered.
        //then the program should wait for players to join each side.
        //once both sides are taken one of the players confirms that they mean to continue to character sellect
        AnimationTimer timer = new AnimationTimer() {

            Controller controller = null;
            @Override
            public void handle(long now) {
                //here should be stuff that happens each frame
                //poll inputs of each gamepad
                //show what what inputs were pressed

                //but test for now
                if(controller == null){
                    controller = ControllerManager.firstControllerWithComponentOn(ControllerManager.getAllUsableControllers());
                }else {
                    controller.poll();

                    EventQueue queue = controller.getEventQueue();

                    Event event = new Event();

                    /* For each object in the queue */
                    while (queue.getNextEvent(event)) {
                        if(event.getComponent().isAnalog()){
                            if(event.getValue() > 0.5 & event.getValue() < -0.5) {
                                System.out.println("analog component name:" + event.getComponent().getName() + " value is:" + event.getValue());
                            }
                        }
                        else {
                            System.out.println("component name:" + event.getComponent().getName() + " value is:" + event.getValue());
                        }

                    }
                }
            }

        };

        timer.start();
    }
    public void playersJoinedAction() throws IOException {
        SceneManager.getInstance().setScene(Scenes.CHARACTER_SELECT);    }
}
