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
import javafx.scene.shape.Rectangle;
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

    public Rectangle healthA;
    double aStartWidth;
    public Rectangle healthB;
    double bStartWidth;

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

            FightGameManager gameManager = new FightGameManager().setFighters(selectedCharacterA, selectedCharacterB).setControllers(playerA, playerB);


            SpriteSheet spriteSheet = new SpriteSheet("CircleFighterNew.png", 22, 22);

            boolean fightOver = false;
            @Override
            public void handle(long l) {
                gameManager.update();

                //display both characters
                updateCharacterImages(spriteSheet, gameManager.characterStateA, ivA);
                updateCharacterImages(spriteSheet, gameManager.characterStateB, ivB);


                //just testing
                updateHealth(gameManager.characterStateA.health, gameManager.characterStateB.health);

                
                //enter animation

                //fightOver = characterStateA.health == 0 || characterStateB.health == 0;
                if(fightOver){
                    fightOverAction();
                }
            }
        };
        timer.start();
    }

    void updateHealth(int a, int b){
        healthA.widthProperty().set((double)aStartWidth/100 * a);
        healthB.widthProperty().set((double)bStartWidth/100 * b);
    }

    void updateCharacterImages(SpriteSheet ss, CharacterState characterState, ImageView iv){
        int[] ii = characterState.currImage();
        Image image = ss.getSprite(ii[1],ii[0]);
        iv.setFitWidth(image.getWidth());

        iv.setImage(image);
        if(characterState.isOnRight){
            iv.setScaleX(-1);
        }
        else {
            iv.setScaleX(1);
        }
        iv.setLayoutX(characterState.rb.rootPosition.x);
        iv.setLayoutY(characterState.rb.rootPosition.y);
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

        aStartWidth = healthA.getWidth();
        bStartWidth = healthB.getWidth();

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
