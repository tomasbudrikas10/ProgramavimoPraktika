package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Vector2d;

public class FightStage {
    Vector2d gravity;
    Box[] gorund;

    public FightStage(Vector2d gravity, Box[] ground){
        this.gravity = gravity;
        this.gorund = ground;
    }
}
