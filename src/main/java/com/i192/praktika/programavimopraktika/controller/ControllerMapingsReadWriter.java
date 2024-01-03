package com.i192.praktika.programavimopraktika.controller;

import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ControllerMapingsReadWriter {

    public static Optional<HashMap<Component, Inputs>> readControllerMappings(Controller controller){
        HashMap<Component, Inputs> inputComponentHashMap = new HashMap<>();
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Path.of("controller_mappings/" + sanitisedControllerName(controller) + ".txt"));

            HashMap<Integer,Component> integerComponentHashMap =  getIntegerComponentHashmapFromController(controller);

            for(int i = 0; i != Inputs.values().length; i++) {
                inputComponentHashMap.put(integerComponentHashMap.get(Integer.parseInt(lines.get(i))), Inputs.values()[i]);
            }
            return Optional.of(inputComponentHashMap);
        }catch (IOException e) {
            return Optional.empty();
        }
    }



    public static void writeControllerMappings(HashMap<Inputs, Component> inputComponentHashMap, Controller controller){



        Path filePath = Path.of("controller_mappings/" + sanitisedControllerName(controller) + ".txt");
        //Path filePath = Path.of(controller.getName() + ".txt");

        HashMap<Component, Integer> componentIntegerHashMap = getComponentIntegerHashmapFromController(controller);

        //take the hashmap and turn it into lines of text to save to a file
        ArrayList<String> lines = new ArrayList<>();
        for(int i = 0; i != Inputs.values().length; i++) {
            //each line contains an integer that is a value in the "componentIntegerHashmap"
            Component component = inputComponentHashMap.get(Inputs.values()[i]);
            String s = componentIntegerHashMap.get(component)+"";
            lines.add(s);
        }

        // Write to the file
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            System.out.println("Data has been written to the file: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void testWrite( List<String> lines, String name){
        Path filePath = Path.of(name + ".txt");


        // Write to the file
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            System.out.println("Data has been written to the file: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static HashMap<Component,Integer> getComponentIntegerHashmapFromController(Controller controller){
        Component[] controllerComponents = controller.getComponents();
        HashMap<Component,Integer> componetIntHashmap = new HashMap<>();
        int iter = 0;
        for(Component component:controllerComponents){

            componetIntHashmap.put(component, iter);
            iter++;
        }

        return componetIntHashmap;
    }




    static String sanitisedControllerName(Controller controller){
        return controller.getName().replace("/","").trim();
    }

    public static HashMap<Integer, Component> getIntegerComponentHashmapFromController(Controller controller){
        Component[] controllerComponents = controller.getComponents();
        HashMap<Integer,Component> hashmap = new HashMap<>();
        int iter = 0;
        for(Component component:controllerComponents){

            hashmap.put(iter, component);
            iter++;
        }

        return hashmap;
    }
}
