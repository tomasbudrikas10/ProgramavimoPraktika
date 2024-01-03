package com.i192.praktika.programavimopraktika.controller;

public enum Inputs {
    UP("Move up"),
    DOWN("Move down"),
    LEFT("Move left"),
    RIGHT("Move right"),
    PUNCH("Perform punch"),
    KICK("Perform kick");

    public final String inputName;

    Inputs(String inputName){
        this.inputName = inputName;
    }




}
