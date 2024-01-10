package com.i192.praktika.programavimopraktika.data;

import com.i192.praktika.programavimopraktika.game.Animation;

import java.util.ArrayList;

public class Frame{
    public Box[] hitBoxes;
    public Box[] hurtBoxes;
    public Box[] colliderBoxes;

    //imageIndex is used to tell which image to take from the spriteSheet
    int imageColl;
    int imageRow;

    public Frame(Box[] hitBoxes, Box[] hurtBoxes, Box[] colliderBoxes, int imageColl, int imageRow){
        this.hitBoxes = hitBoxes;
        this.hurtBoxes = hurtBoxes;
        this.colliderBoxes = colliderBoxes;
        this.imageColl = imageColl;
        this.imageRow = imageRow;
    }

    public Frame(String s){
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
