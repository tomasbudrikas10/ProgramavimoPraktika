package com.i192.praktika.programavimopraktika.controller;

public enum Input {
    UP,DOWN,LEFT,RIGHT,PUNCH,KICK;

    public String getInputName(Input input){
        return switch (input) {
            case UP -> "Move up";
            case DOWN -> "Move down";
            case LEFT -> "Move left";
            case RIGHT -> "Move right";
            case PUNCH -> "Perform punch";
            case KICK -> "Perform kick";
            default -> null;
        };
    }
}
