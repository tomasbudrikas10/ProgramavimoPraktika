package com.i192.praktika.programavimopraktika.controller;

import net.java.games.input.*;

import java.util.ArrayList;
import java.util.List;

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

    public static void pollController(Controller c) {


        c.poll();

        EventQueue queue = c.getEventQueue();

        Event event = new Event();

        /* For each object in the queue */
        while (queue.getNextEvent(event)) {

            String contName = c.getName();

            long time  = event.getNanos();
            Component component = event.getComponent();

            String compName = component.getName();

            float value = event.getValue();



            if (value != 0.0) {
                System.out.println(c.getType() + " " + c.getComponents().length);
            }
        }
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
}
