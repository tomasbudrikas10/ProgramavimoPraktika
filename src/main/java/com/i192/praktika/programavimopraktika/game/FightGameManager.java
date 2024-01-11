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
        return this;
    }

    public FightGameManager setControllers(ConfiguredController A, ConfiguredController B){
        playerA = A;
        playerB = B;
        return this;
    }
    public FightStage fightStage = new FightStage(new Vector2d(0, 22), new Box(new Vector2d(0, 350), new Vector2d(600, 1000000)));
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
        }
        else{
            cs.rb.rootPosition.add(cs.getFrame().translation);
        }

    }
    public void manageOverlaps(){
        //character ground overlap
        characterGroundOverlap(characterStateA);
        characterGroundOverlap(characterStateB);


        //toDo:character to character overlap
        Box[] boxesA = characterStateA.getColliderBoxes();
        Box[] boxesB = characterStateA.getColliderBoxes();

        for(Box ba: boxesA){
            for(Box bb: boxesB){
                boolean c = Collisions.collides(ba, bb, characterStateA.rb.rootPosition, characterStateB.rb.rootPosition);
                if(c){
                    Vector2d v = Collisions.push(ba, bb, characterStateA.rb.rootPosition, characterStateB.rb.rootPosition);
                    characterStateA.rb.rootPosition.add(Vector2d.mul(v,-0.5));
                    characterStateB.rb.rootPosition.add(Vector2d.mul(v,0.5));
                    characterStateA.rb.velocity.setY(0);
                    characterStateB.rb.velocity.setY(0);
                }
            }
        }

    }

    private void characterGroundOverlap(CharacterState characterState) {
        Box[] boxes = characterState.getColliderBoxes();
        for(Box b: boxes){
            boolean c = Collisions.collides(fightStage.gorund, b, Vector2d.ZERO, characterState.rb.rootPosition);
            if(c){
                //System.out.println("GGS");
                Vector2d v = Collisions.push(fightStage.gorund, b, Vector2d.ZERO, characterState.rb.rootPosition);
                characterState.rb.rootPosition.add(v);
                characterState.rb.velocity.setY(0);
            }
        }
    }

    public void manageHit(){
        //A on B
        //B on A
    }

    public void advanceAnimations(){
        advanceAnimation(characterStateA);
        advanceAnimation(characterStateB);

        imputsToAnimation(playerA, characterStateA);
        imputsToAnimation(playerB, characterStateB);

    }

    void imputsToAnimation(ConfiguredController configuredControllerler, CharacterState state){
        if(configuredControllerler.latestChanges.length > 0){
            Pair<Inputs,Boolean> tehInp = configuredControllerler.latestChanges[0];
            if(state.animation == 0 && tehInp.getValue()){
                int o = 0;
                if(state.isOnRight){o = 1;}
                state.animation = state.character.inputAnimationMap[o].get(tehInp.getKey());
                state.animationFrame = 0;
            }
        }
    }
    void advanceAnimation(CharacterState cs){
        cs.animationFrame++;
        if(cs.animationFrame == cs.character.animations[cs.animation].frames.length){
            cs.animation = 0;
            cs.animationFrame = 0;
        }
    }
}
