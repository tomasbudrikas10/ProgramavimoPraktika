package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.game.Character;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class OneVSOneFight implements Initialisable{

    ConfiguredController playerA = null;
    ConfiguredController playerB = null;

    Character selectedCharacterA = null;
    Character selectedCharacterB = null;

    public ImageView ivA;

    public ImageView ivB;

    public void setPlayers(ConfiguredController A, ConfiguredController B){
        this.playerA = A;
        this.playerB = B;
    }

    public void setSelectedCharacters(Character A , Character B){
        this.selectedCharacterA = A;
        this.selectedCharacterB = B;
    }



    public void gameLoop(){




        AnimationTimer timer = new AnimationTimer() {

            CharacterState characterStateA = new CharacterState(100, -100, 0);
            CharacterState characterStateB = new CharacterState(100, 100, 0);

            SpriteSheet spriteSheet = new SpriteSheet("CircleFighter.png", 6, 5, 22, 22);

            int i = 0;
            boolean fightOver = false;
            @Override
            public void handle(long l) {
                playerA.updateLatestChanges();
                playerB.updateLatestChanges();



                ivA.setImage(spriteSheet.getSprite(i,3));
                i++;
                if(i>3){i = 0;}



                //do gravity
                //enter animation

                fightOver = characterStateA.health == 0 || characterStateB.health == 0;
                if(fightOver){
                    fightOverAction();
                }
            }
        };
        timer.start();
    }

    public void fightOverAction(){

    }

    @Override
    public void initialise() {
        //ivA = new ImageView();
        //ivB = new ImageView();
        gameLoop();
    }
}
