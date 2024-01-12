package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Frame;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.physics.RigidBody;
import javafx.util.Pair;

public class CharacterState{

    public Fighter character;
    public int health;
    public RigidBody rb;
    public int animation = 0;
    public int animationFrame = 0;

    public boolean isOnRight;
    public boolean isGrounded = false;

    public Pair<Inputs, Boolean>[] loopingImputs;

    public Inputs looping = null;



    public CharacterState(Fighter character, int startHealth, Vector2d startPosition){
        loopingImputs = new Pair[Inputs.values().length];
        for(int i = 0; i < loopingImputs.length; i++){
            loopingImputs[i] = new Pair<>(Inputs.values()[i],false);
        }
        this.character = character;
        this.health = startHealth;
        this.rb = new RigidBody(startPosition);
    }

    public Box[] getColliderBoxes(){
        return character.animations[animation].frames[animationFrame].colliderBoxes;
    }

    public Frame getFrame(){
        return character.animations[animation].frames[animationFrame];
    }

    public int[] currImage(){
        return new int[]{animation, animationFrame};
    }
    public Box[] getHitBoxes(){
        return character.animations[animation].frames[animationFrame].hitBoxes;
    }

    public Box[] getHurtBoxes(){
        return character.animations[animation].frames[animationFrame].hurtBoxes;
    }
}