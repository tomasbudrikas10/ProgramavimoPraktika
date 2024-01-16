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

    public int pipsLeft = 2;

    public Inputs looping = null;

    int performingAttackLevel = 0;



    public void reset(Vector2d startPosition){
        this.health = character.defaultHealth;
        this.rb = new RigidBody(startPosition);
    }


    public CharacterState(Fighter character, Vector2d startPosition){
        this.character = character;
        this.health = character.defaultHealth;
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