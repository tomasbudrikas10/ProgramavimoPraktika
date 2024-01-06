package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.physics.Collisions;

public class FightGameManager {

    public FightGameManager(Character A, Character B){
        this.characterStateA = new CharacterState(A,100, new Vector2d(-1, 0));
        this.characterStateB = new CharacterState(B,100, new Vector2d(1, 0));
    }
    FightStage fightStage = new FightStage(new Vector2d(0, -1), new Box(new Vector2d(-100, 0), new Vector2d(100, -10)));
    CharacterState characterStateA;
    CharacterState characterStateB;

    public void update(){

        applyGravity();
        moveCharacters();
        manageOverlaps();


        // do bounce,
        manageHit();
        advanceAnimations();
    }

    public void applyGravity(){
        characterStateA.rb.velocity.add(fightStage.gravity);
        characterStateB.rb.velocity.add(fightStage.gravity);
    }

    public void moveCharacters(){
        characterStateA.rb.move(1f/30);
        characterStateB.rb.move(1f/30);
    }
    public void manageOverlaps(){
        //character ground overlap
        characterGroundOverlap(characterStateA);
        characterGroundOverlap(characterStateB);


        //toDo:character to character overlap
    }

    private void characterGroundOverlap(CharacterState characterState) {
        Box[] boxes = characterState.getColliderBoxes();
        for(Box b: boxes){
            boolean c = Collisions.collides(fightStage.gorund, b, Vector2d.ZERO, characterState.rb.rootPosition);
            if(c){
                Vector2d v = Collisions.push(fightStage.gorund, b, Vector2d.ZERO, characterState.rb.rootPosition);
                characterState.rb.rootPosition.add(v);
            }
        }
    }

    public void manageHit(){
        //A on B
        //B on A
    }

    public void advanceAnimations(){
        
    }
}
