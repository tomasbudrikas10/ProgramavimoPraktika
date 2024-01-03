package com.i192.praktika.programavimopraktika.controller;

import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.HashMap;

public class ConfiguredController {

    HashMap<Component, Inputs> componentInputsHashMap;

    ConfiguredController(Controller controller){
        componentInputsHashMap = ControllerMapingsReadWriter.getComponentInputsHashmapFromController(controller);
    }
}
