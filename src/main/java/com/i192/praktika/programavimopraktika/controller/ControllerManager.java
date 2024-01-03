package com.i192.praktika.programavimopraktika.controller;

import net.java.games.input.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static net.java.games.input.Controller.Type.*;


public class ControllerManager {

    public static void test(){
        for(Controller c: getAllUsableControllers()){
            ControllerMapingsReadWriter.testWrite(listAllComponents(c), c.getName());
        }
    }

    public static Controller firstControllerWithComponentOn(Controller[] cs){
        //will return the first controller with that has at least one non-analog component with a value of 1, or null if no such controller in provided list
        for(Controller c:cs){
            if(getComponentOn(c) != null){
                return c;
            }
        }
        return null;
    }

    public static Component getComponentOn(Controller c) {
        //will return the first component with a value of 1, or null

        c.poll();

        EventQueue queue = c.getEventQueue();

        Event event = new Event();

        /* For each object in the queue */
        while (queue.getNextEvent(event)) {

            if(!event.getComponent().isAnalog()) {
                if (event.getValue() == 1.0) {
                    return event.getComponent();
                }
            }
        }

        return null;
    }


    public static List<String> listAllComponents(Controller c){
        ArrayList<String> list = new ArrayList<>();

        for(Component comp: c.getComponents()){
            list.add(comp.toString());

        }

        return list;
    }

    public static Controller[] getAllControllers(){
        return ControllerEnvironment.getDefaultEnvironment().getControllers();
    }

    public static Controller[] getAllUsableControllers(){
        Controller[] cs = getAllControllers();
        ArrayList<Controller> usable = new ArrayList<Controller>();

        for(Controller c: cs){
            if(c.getType() == GAMEPAD || c.getType() == KEYBOARD || c.getType() == STICK){
                usable.add(c);
            }
        }
        return usable.toArray(new Controller[0]);
    }
    public static ArrayList<ConfiguredController> getConfiguredControllers(){
        ArrayList<ConfiguredController> ccList = new ArrayList<>();

        Controller[] cArr = ControllerManager.getAllUsableControllers();

        for(Controller c: cArr){
            Optional<ConfiguredController> optional = makeConfiguredController(c);

            optional.ifPresent(ccList::add);//this is same as: if(optional.isPresent()){ ccList.add(optional.get()); }
        }
        return ccList;
    }

    public static Optional<ConfiguredController> makeConfiguredController(Controller controller){
        Optional<HashMap<Component, Inputs>> optional = ControllerMapingsReadWriter.readControllerMappings(controller);

        return optional.map(componentInputsHashMap -> new ConfiguredController(controller, componentInputsHashMap));
    }
}
