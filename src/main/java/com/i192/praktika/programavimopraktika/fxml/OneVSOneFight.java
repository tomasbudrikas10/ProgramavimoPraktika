package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.game.Character;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import javafx.animation.AnimationTimer;

public class OneVSOneFight implements Initialisable{

    ConfiguredController playerA = null;
    ConfiguredController playerB = null;

    Character selectedCharacterA = null;
    Character selectedCharacterB = null;

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

            boolean fightOver = false;
            @Override
            public void handle(long l) {
                playerA.updateLatestChanges();
                playerB.updateLatestChanges();



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
        gameLoop();
    }
}
