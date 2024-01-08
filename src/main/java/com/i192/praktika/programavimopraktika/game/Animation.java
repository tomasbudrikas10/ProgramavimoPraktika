package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.data.Frame;
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





}
