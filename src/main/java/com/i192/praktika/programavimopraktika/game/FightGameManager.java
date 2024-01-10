package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.physics.Collisions;

public class FightGameManager {

    public FightGameManager(Fighter A, Fighter B){
        this.characterStateA = new CharacterState(A,100, new Vector2d(100, 200));
        this.characterStateB = new CharacterState(B,100, new Vector2d(500, 200));
    }
    public FightStage fightStage = new FightStage(new Vector2d(0, 22), new Box(new Vector2d(0, 350), new Vector2d(600, 1000000)));
    public CharacterState characterStateA;
    public CharacterState characterStateB;

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
        //not yet...
    }
}
