package com.i192.praktika.programavimopraktika.controller;

import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.HashMap;

public class ConfiguredController {

    public HashMap<Component, Inputs> componentInputsHashMap;
    public Controller controller;

    ConfiguredController(Controller controller, HashMap<Component, Inputs> componentInputsHashMap){
        this.controller = controller;
        this.componentInputsHashMap = componentInputsHashMap;
    }
}
