package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.Characters;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.game.Fighter;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import com.i192.praktika.programavimopraktika.game.FightGameManager;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OneVSOneFight implements Initialisable{

    ConfiguredController playerA = null;
    ConfiguredController playerB = null;

    Fighter selectedCharacterA = null;
    Fighter selectedCharacterB = null;

    public Pane pane;

    public ImageView ivA;

    public ImageView ivB;

    public ImageView ground;
    public ImageView background;

    public void setPlayers(ConfiguredController A, ConfiguredController B){
        this.playerA = A;
        this.playerB = B;
    }

    public void setSelectedCharacters(Fighter A , Fighter B){
        this.selectedCharacterA = A;
        this.selectedCharacterB = B;
    }



    public void gameLoop(){
        selectedCharacterA = Characters.getFighter(Characters.BOB_THE_CAT);
        selectedCharacterB = Characters.getFighter(Characters.ROB_THE_CAT);


        AnimationTimer timer = new AnimationTimer() {

            FightGameManager gameManager = new FightGameManager(selectedCharacterA, selectedCharacterB);

            SpriteSheet spriteSheet = new SpriteSheet("CircleFighter.png", 6, 5, 22, 22);

            int i = 0;
            int o = 0;
            boolean fightOver = false;
            @Override
            public void handle(long l) {
                playerA.updateLatestChanges();
                playerB.updateLatestChanges();



                gameManager.update();

                //display both characters
                updateCharacterImages(spriteSheet, gameManager.characterStateA, ivA);
                updateCharacterImages(spriteSheet, gameManager.characterStateB, ivB);


                //just testing



                //enter animation

                //fightOver = characterStateA.health == 0 || characterStateB.health == 0;
                if(fightOver){
                    fightOverAction();
                }
            }
        };
        timer.start();
    }

    void updateCharacterImages(SpriteSheet ss, CharacterState characterState, ImageView iv){
        int[] ii = characterState.currImage();
        Image image = ss.getSprite(ii[0],ii[1]);
        iv.setFitWidth(image.getWidth());

        iv.setImage(image);
        iv.setLayoutX(characterState.rb.rootPosition.x);
        iv.setLayoutY(characterState.rb.rootPosition.y);
    }

    void imputsToAnimation(ConfiguredController configuredControllerler, CharacterState state){
        Pair<Inputs,Boolean> tehInp = configuredControllerler.latestChanges[0];
        if(state.animation == 0 && tehInp.getValue()){
            state.animation = state.character.inputAnimationMap.get(tehInp.getKey());
            state.animationFrame = 0;
        }
    }

    public void fightOverAction(){

    }

    @Override
    public void initialise() {


        try {
            ground.setImage(new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/gorund.png")));
            background.setImage(new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/backgorund.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ground.preserveRatioProperty().set(false);
        background.preserveRatioProperty().set(false);

        ground.setLayoutX(0);
        ground.setLayoutY(350);
        ground.setFitWidth(600);
        ground.setFitHeight(50);

        background.setLayoutX(0);
        background.setLayoutY(0);
        background.setFitWidth(600);
        background.setFitHeight(400);

        gameLoop();
    }
}
