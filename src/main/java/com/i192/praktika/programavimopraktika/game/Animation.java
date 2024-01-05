package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.controller.Inputs;
import javafx.scene.image.Image;
import javafx.util.Pair;
import net.java.games.input.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
//import javax.vecmath;

public class Animation {

    static String animationsFolderPath = "/com/i192/praktika/programavimopraktika/animation";
    Frame[] frames;

    String spriteSheetName;

    Animation(Frame[] frames, String spriteSheetName){
        this.frames = frames;
        this.spriteSheetName = spriteSheetName;
    }

    public Frame[] readFrameData(String fileName){
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Path.of(animationsFolderPath + "/frame_datas/" + fileName));
        }catch (IOException e) {
            return null;
        }

        ArrayList<Frame> frameList = new ArrayList<>();


        for(String line:lines) {
            frameList.add(new Frame(line));
        }

        return frameList.toArray(new Frame[0]);
    }

    public void writeFrameData(Frame[] frames, String fileName){
        Path filePath = Path.of(animationsFolderPath + "/frame_datas/" +  fileName);

        ArrayList<String> lines = new ArrayList<>();
        for(Frame frame:frames){
            lines.add(frame.toString());
        }



        // Write to the file
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            System.out.println("Data has been written to the file: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }


    static class Vector2d{
        float x;
        float y;

        Vector2d(float x, float y){
            this.x = x;
            this.y = y;
        }
        Vector2d(String s){
            String[] ss = s.split(";");
            this.x = Float.parseFloat(ss[0]);
            this.y = Float.parseFloat(ss[1]);
        }
        @Override
        public String toString() {
            return x + ";" + y;
        }
    }
    static class Box{
        Vector2d offSet;
        Vector2d dimentions;

        Box(Vector2d offSet, Vector2d dimentions){
            this.offSet = offSet;
            this.dimentions = dimentions;
        }
        Box(String s){
            String[] ss = s.split("~");
            this.offSet = new Vector2d(ss[0]);
            this.dimentions = new Vector2d(ss[1]);
        }
        @Override
        public String toString() {
            return offSet.toString() + "~" +dimentions.toString();
        }
    }
    static class Frame{
        Box[] hitBoxes;
        Box[] hurtBoxes;
        Box[] colliderBoxes;

        //imageIndex is used to tell which image to take from the spriteSheet
        int imageColl;
        int imageRow;

        Frame(Box[] hitBoxes, Box[] hurtBoxes, Box[] colliderBoxes, int imageColl, int imageRow){
            this.hitBoxes = hitBoxes;
            this.hurtBoxes = hurtBoxes;
            this.colliderBoxes = colliderBoxes;
            this.imageColl = imageColl;
            this.imageRow = imageRow;
        }

        Frame(String s){
            String[] ss = s.split("hitBoxes:");
            ss = ss[0].split("hurtBoxes:");
            String[] hitBoxesStrings = ss[0].split("#");

            ss = ss[1].split("colliderBoxes:");
            String[] hurtBoxesStrings = ss[0].split("#");
            ss = ss[1].split("imageColl:");
            String[] colliderBoxesStrings = ss[0].split("#");
            this.imageColl = Integer.parseInt(ss[0]);
            ss = ss[1].split("imageRow:");
            this.imageRow = Integer.parseInt(ss[1]);


            ArrayList<Box> hitBoxesList = new ArrayList<>();
            ArrayList<Box> hurtBoxesList = new ArrayList<>();;
            ArrayList<Box> colliderBoxesList = new ArrayList<>();;

            for(String string:hitBoxesStrings){
                hitBoxesList.add(new Box(string));
            }

            for(String string:hurtBoxesStrings){
                hurtBoxesList.add(new Box(string));
            }

            for(String string:colliderBoxesStrings){
                colliderBoxesList.add(new Box(string));
            }

            this.hitBoxes = hitBoxesList.toArray(new Box[0]);
            this.hurtBoxes = hurtBoxesList.toArray(new Box[0]);
            this.colliderBoxes = colliderBoxesList.toArray(new Box[0]);
        }

        @Override
        public String toString() {
            String s = "hitBoxes:";
            for(Box b:hitBoxes){
                s = s + b.toString() + "#";
            }
            s = s + "hurtBoxes:";
            for(Box b:hurtBoxes){
                s = s + b.toString() + "#";
            }
            s = s + "colliderBoxes:";
            for(Box b:colliderBoxes){
                s = s + b.toString() + "#";
            }
            s = s + "imageColl:" + imageColl + "imageRow:" + imageRow;
            return s;
        }
    }
}
