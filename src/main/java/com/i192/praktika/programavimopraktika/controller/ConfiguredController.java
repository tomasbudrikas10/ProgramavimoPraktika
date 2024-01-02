package com.i192.praktika.programavimopraktika.controller;


import net.java.games.input.Controller;

import java.util.ArrayList;

public class ConfiguredController {
    public ConfiguredController(Controller controller){
        this.controller = controller;
    }


    Controller controller;

    ArrayList<Input> latest;

    public void update() {

    }

}
