package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.physics.Collisions;
import javafx.util.Pair;

public class FightGameManager {

    public FightGameManager setFighters(Fighter A, Fighter B){
        this.characterStateA = new CharacterState(A,A.defaultHealth, new Vector2d(100, 200));
        this.characterStateB = new CharacterState(B,B.defaultHealth, new Vector2d(500, 200));

        Box[] groundBoxes = new Box[3];
        groundBoxes[0] = new Box(new Vector2d(0, 350), new Vector2d(600, 1000000));
        groundBoxes[1] = new Box(new Vector2d(-100, 0), new Vector2d(0, 500));
        groundBoxes[2] = new Box(new Vector2d(600, 0), new Vector2d(700, 500));



        this.fightStage = new FightStage(new Vector2d(0, 22), groundBoxes);
        return this;
    }

    public FightGameManager setControllers(ConfiguredController A, ConfiguredController B){
        playerA = A;
        playerB = B;
        return this;
    }

    public FightStage fightStage;
    public CharacterState characterStateA;
    public CharacterState characterStateB;

    ConfiguredController playerA;
    ConfiguredController playerB;

    public void update(){
        getLatestInputs();
        applyGravity();
        moveCharacters();
        checkSides();
        manageOverlaps();



        // do bounce,
        manageHit();
        advanceAnimations();
    }

    public void getLatestInputs(){
        playerA.updateLatestChanges();
        playerB.updateLatestChanges();
    }

    public void applyGravity(){
        characterStateA.rb.velocity.add(fightStage.gravity);
        characterStateB.rb.velocity.add(fightStage.gravity);
    }

    public void checkSides(){
        if(characterStateA.rb.rootPosition.x > characterStateB.rb.rootPosition.x){
            characterStateA.isOnRight = true;
            characterStateB.isOnRight = false;
        }
        else {
            characterStateA.isOnRight = false;
            characterStateB.isOnRight = true;
        }
    }

    public void moveCharacters(){
        moveCharacter(characterStateA);
        moveCharacter(characterStateB);
    }

    void moveCharacter(CharacterState cs){
        cs.rb.move(1f/30);
        if(cs.isOnRight){
            cs.rb.rootPosition.add(Vector2d.flipX(cs.getFrame().translation));
            cs.rb.velocity.add(Vector2d.flipX(cs.getFrame().velosityChange));
        }
        else{
            cs.rb.rootPosition.add(cs.getFrame().translation);
            cs.rb.velocity.add(cs.getFrame().velosityChange);
        }

    }
    public void manageOverlaps(){
        //character ground overlap
        characterGroundOverlap(characterStateA);
        characterGroundOverlap(characterStateB);


        Box[] boxesA = characterStateA.getColliderBoxes();
        Box[] boxesB = characterStateB.getColliderBoxes();

        for(Box ba: boxesA){
            for(Box bb: boxesB){
                boolean c = Collisions.collides(ba, bb, characterStateA.rb.rootPosition, characterStateB.rb.rootPosition);
                if(c){
                    Vector2d v = Collisions.push(ba, bb, characterStateA.rb.rootPosition, characterStateB.rb.rootPosition);
                    characterStateA.rb.rootPosition.add(Vector2d.mul(v,-0.5));
                    characterStateB.rb.rootPosition.add(Vector2d.mul(v,0.5));
                    characterStateA.rb.velocity.noPosY();
                    characterStateB.rb.velocity.noPosY();
                }
            }
        }

    }

    private void characterGroundOverlap(CharacterState characterState) {
        Box[] boxes = characterState.getColliderBoxes();
        for(Box b: boxes){
            for(Box bg:fightStage.gorund){
                boolean c = Collisions.collides(bg, b.mabyFlippedX(11, characterState.isOnRight), Vector2d.ZERO, characterState.rb.rootPosition);
                if(c){
                    //System.out.println("GGS");
                    Vector2d v = Collisions.push(bg, b.mabyFlippedX(11, characterState.isOnRight), Vector2d.ZERO, characterState.rb.rootPosition);
                    characterState.rb.rootPosition.add(v);

                    //toDo:actually should only take avay the part of velocity that is pointing towards the obstacle
                    characterState.rb.velocity.noPosY();
                    characterState.isGrounded = true;
                }
            }

        }
    }

    public void manageHit(){

        Box[] hitBoxesA = characterStateA.getHitBoxes();
        Box[] hurtBoxesA = characterStateA.getHurtBoxes();

        Box[] hitBoxesB = characterStateB.getHitBoxes();
        Box[] hurtBoxesB = characterStateB.getHurtBoxes();

        boolean aHitB = false;
        boolean bHitA = false;

        for(Box ba: hitBoxesA){
            for(Box bb: hurtBoxesB){
                boolean c = Collisions.collides(ba.mabyFlippedX(11, characterStateA.isOnRight), bb.mabyFlippedX(11, characterStateB.isOnRight), characterStateA.rb.rootPosition, characterStateB.rb.rootPosition);
                if(c){
                    aHitB = true;

                    characterStateB.health-=10;

                    Vector2d v = Collisions.push(ba.mabyFlippedX(11, characterStateA.isOnRight), bb.mabyFlippedX(11, characterStateB.isOnRight), characterStateA.rb.rootPosition, characterStateB.rb.rootPosition);

                    characterStateB.rb.rootPosition.add(Vector2d.mul(v,0.5));
                    //characterStateB.rb.velocity.add(Vector2d.mul(v, -1));
                    characterStateB.animation = 5;
                    characterStateB.animationFrame = 0;
                }
            }
        }

        for(Box ba: hitBoxesB){
            for(Box bb: hurtBoxesA){
                boolean c = Collisions.collides(ba.mabyFlippedX(11, characterStateB.isOnRight), bb.mabyFlippedX(11, characterStateA.isOnRight), characterStateB.rb.rootPosition, characterStateA.rb.rootPosition);
                if(c){
                    bHitA = true;

                    characterStateA.health-=10;

                    Vector2d v = Collisions.push(ba.mabyFlippedX(11, characterStateB.isOnRight), bb.mabyFlippedX(11, characterStateA.isOnRight), characterStateB.rb.rootPosition, characterStateA.rb.rootPosition);

                    characterStateA.rb.rootPosition.add(Vector2d.mul(v,0.5));
                    //characterStateA.rb.velocity.add(Vector2d.mul(v, -1));
                    characterStateA.animation = 5;
                    characterStateA.animationFrame = 0;
                }
            }
        }
    }

    public void advanceAnimations(){
        advanceAnimation(characterStateA);
        advanceAnimation(characterStateB);

        imputsToAnimation(playerA, characterStateA);
        imputsToAnimation(playerB, characterStateB);

    }

    void imputsToAnimation(ConfiguredController configuredControllerler, CharacterState state){
        if(configuredControllerler.latestChanges.length > 0){

            for(Pair<Inputs,Boolean> tehInp:configuredControllerler.latestChanges){
                //Pair<Inputs,Boolean> tehInp = configuredControllerler.latestChanges[0];
                if(tehInp.getValue()){
                    if(state.character.inputDownBoolLamdaMap.get(tehInp.getKey()).apply(state)){
                        int o = 0;
                        if(state.isOnRight){o = 1;}
                        state.animation = state.character.inputAnimationMap[o].get(tehInp.getKey());
                        state.animationFrame = 0;
                    }
                }
                else
                {
                    if(state.character.inputUpBoolLamdaMap.get(tehInp.getKey()).apply(state)){
                        //int o = 0;
                        //if(state.isOnRight){o = 1;}
                        //state.animation = state.character.inputAnimationMap[o].get(tehInp.getKey());
                        //state.animationFrame = 0;
                    }
                }
            }



        }
    }
    void advanceAnimation(CharacterState cs){
        cs.animationFrame++;
        if(cs.animationFrame == cs.character.animations[cs.animation].frames.length){
            if(cs.looping == null) {
                cs.animation = 0;
            } else {
                int o = 0;
                if(cs.isOnRight){o = 1;}
                cs.animation = cs.character.inputAnimationMap[o].get(cs.looping);
            }

            cs.animationFrame = 0;
        }
    }
}
