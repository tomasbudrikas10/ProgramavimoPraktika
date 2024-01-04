package com.i192.praktika.programavimopraktika.game;

 public class CharacterState{
    public int health;
    public float positionX;
    public float positionY;
    public int animation = 0;
    public int animationFrame = 0;



    public CharacterState(int startHealth, float positionX, float positionY){
        this.health = startHealth;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}