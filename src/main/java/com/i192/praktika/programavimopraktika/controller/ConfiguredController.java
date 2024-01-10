package com.i192.praktika.programavimopraktika.controller;

import javafx.util.Pair;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfiguredController{

    public HashMap<Component, Inputs> componentInputsHashMap;
    public Controller controller;

    public Pair<Inputs, Boolean>[] latestChanges;

    ConfiguredController(Controller controller, HashMap<Component, Inputs> componentInputsHashMap){
        this.controller = controller;
        this.componentInputsHashMap = componentInputsHashMap;
    }

    public void updateLatestChanges(){
        controller.poll();

        EventQueue queue = controller.getEventQueue();

        Event event = new Event();
        ArrayList<Pair<Inputs, Boolean>> changes = new ArrayList<Pair<Inputs, Boolean>>();

        int i = 0;
        /* For each object in the queue */
        while (queue.getNextEvent(event)) {
            Inputs input = componentInputsHashMap.get(event.getComponent());
            if(input != null){
                i++;

                changes.add(new Pair<>(input,event.getValue() >= 1.0));
            }
        }
        latestChanges = new Pair[i];
        latestChanges = changes.toArray(latestChanges);
    }
}
