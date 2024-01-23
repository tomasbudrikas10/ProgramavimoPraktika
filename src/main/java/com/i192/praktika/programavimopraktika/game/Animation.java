package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Frame;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import com.i192.praktika.programavimopraktika.spritesheet.BoxTypes;
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

    public SpriteSheet spriteSheet;
    static String animationsFolderPath = "src/main/resources/com/i192/praktika/programavimopraktika/animation";
    public Frame[] frames;

    Animation(Frame[] frames){
        this.frames = frames;

    }
    Animation(String spriteSheetName, String frameData){
        this.frames = readFrameData(frameData);
        this.spriteSheet = new SpriteSheet(spriteSheetName, 200, 200);

    }

    public Frame[] readFrameDataOLD(String fileName){
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

    public Frame[] readFrameData(String frameDataName){
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Path.of(animationsFolderPath + "/frame_datas/" + frameDataName));
        }catch (IOException e) {
            System.out.println(animationsFolderPath + "/frame_datas/" + frameDataName);
            return null;
        }

        int collCount = Integer.parseInt(lines.get(1));
        Frame[] frameList = new Frame[collCount];
        String[] velocityStrings = lines.get(3).split(" ");
        String[] translationStrings = lines.get(2).split(" ");


        String[][] boxStrings = new String[lines.size() - 5][7] ;
        for(int i = 4; i < lines.size() - 1; i++){
            boxStrings[i - 4] = lines.get(i).split(" ");
        }

        ArrayList<Box>[] hitBoxArrList = new ArrayList[collCount];
        ArrayList<Box>[] hurtBoxArrList = new ArrayList[collCount];
        ArrayList<Box>[] collisionBoxArrList = new ArrayList[collCount];

        for(int i = 0; i < collCount; i++){
            hitBoxArrList[i] = new ArrayList<>();
            hurtBoxArrList[i] = new ArrayList<>();
            collisionBoxArrList[i] = new ArrayList<>();
        }

        for(String[] arr: boxStrings){

            int x = Integer.parseInt(arr[5]);
            int y = Integer.parseInt(arr[6]);
            Vector2d topLeft = new Vector2d((double)x, (double)y);
            Vector2d bottomRight= new Vector2d((double)(Integer.parseInt(arr[3]) + x), (double)(Integer.parseInt(arr[4]) + y));

            if(arr[0].equals("collisionBox")){
                collisionBoxArrList[Integer.parseInt(arr[2])].add(new Box(topLeft, bottomRight));
            }
            if(arr[0].equals("hurtBox")){
                hurtBoxArrList[Integer.parseInt(arr[2])].add(new Box(topLeft, bottomRight));
            }
            if(arr[0].equals("hitBox")){
                hitBoxArrList[Integer.parseInt(arr[2])].add(new Box(topLeft, bottomRight));
            }
        }

        for(int i = 0; i < collCount; i++) {
            frameList[i] = new Frame (hitBoxArrList[i].toArray(new Box[0]), hurtBoxArrList[i].toArray(new Box[0]), collisionBoxArrList[i].toArray(new Box[0]), i, 0, new Vector2d(translationStrings[i]), new Vector2d(velocityStrings[i]));
        }

        return frameList;
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





}
