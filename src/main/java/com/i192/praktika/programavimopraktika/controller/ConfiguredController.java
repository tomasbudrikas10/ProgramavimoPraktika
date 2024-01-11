package com.i192.praktika.programavimopraktika.controller;

import javafx.util.Pair;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfiguredController{
public String playerName;
public String getPlayerName(){
    return playerName;
}
public void setPlayerName(String playerName){
    this.playerName= playerName;
}

    public HashMap<Component, Inputs> componentInputsHashMap;
    public Controller controller;

    public Pair<Inputs, Boolean>[] latestChanges;

    ConfiguredController(Controller controller, HashMap<Component, Inputs> componentInputsHashMap){
        this.controller = controller;
        this.componentInputsHashMap = componentInputsHashMap;
        this.latestChanges = new Pair[0];
    }

    public void updateLatestChanges(){
        controller.poll();

        EventQueue queue = controller.getEventQueue();

        Event event = new Event();
        ArrayList<Pair<Inputs, Boolean>> changes = new ArrayList<>();

        /* For each object in the queue */
        while (queue.getNextEvent(event)) {
            Inputs input = componentInputsHashMap.get(event.getComponent());
            if(input!= null){
                changes.add(new Pair<>(input,event.getValue() >= 1.0));
            }
        }

        latestChanges = changes.toArray(new Pair[0]);
    }
}
