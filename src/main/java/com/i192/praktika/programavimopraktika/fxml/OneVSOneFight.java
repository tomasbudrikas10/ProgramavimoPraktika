package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.game.Character;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import com.i192.praktika.programavimopraktika.game.FightGameManager;
import com.i192.praktika.programavimopraktika.game.FightStage;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Box;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OneVSOneFight implements Initialisable{

    ConfiguredController playerA = null;
    ConfiguredController playerB = null;

    Character selectedCharacterA = null;
    Character selectedCharacterB = null;

    public Pane pane;

    public ImageView ivA;

    public ImageView ivB;

    public ImageView ground;
    public ImageView background;

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

            FightGameManager gameManager = new FightGameManager(selectedCharacterA, selectedCharacterB);

            SpriteSheet spriteSheet = new SpriteSheet("CircleFighter.png", 6, 5, 22, 22);

            int i = 0;
            int o = 0;
            boolean fightOver = false;
            @Override
            public void handle(long l) {
                playerA.updateLatestChanges();
                playerB.updateLatestChanges();

                //display both characters
                updateCharacterImages(spriteSheet, gameManager.characterStateA, ivA);
                updateCharacterImages(spriteSheet, gameManager.characterStateB, ivB);


                //just testing
                ivA.setLayoutX(100);
                ivA.setLayoutY(100);


                ivB.setLayoutX(200);
                ivB.setLayoutY(200);

                ivA.setImage(spriteSheet.getSprite(i,2));
                i++;
                if(i>4){i = 0;}


                ivA.setTranslateX(o);
                o++;
                if(o>100){o = 0;}


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
        iv.setImage(ss.getSprite(ii[0],ii[1]));
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

        gameLoop();
    }
}
