package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.Characters;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.game.Fighter;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import com.i192.praktika.programavimopraktika.game.FightGameManager;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
    double aWidth;
    public Rectangle healthB;
    double bWidth;
    public Text timeLeftText;

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



            @Override
            public void handle(long now) {
                gameManager.update(now);

                //display both characters
                updateCharacterImages(spriteSheet, gameManager.characterStateA, ivA);
                updateCharacterImages(spriteSheet, gameManager.characterStateB, ivB);


                updateHealth(gameManager.characterStateA, gameManager.characterStateB);

                updateTime(gameManager);

                if(gameManager.bWon || gameManager.aWon){
                    fightOverAction();
                }
            }
        };


        timer.start();
    }

    void updateHealth(CharacterState a, CharacterState b){
        healthA.setWidth(aWidth/100 * a.health);
        healthB.setWidth(bWidth/100 * b.health);
    }

    void updatePips(CharacterState a, CharacterState b){

    }

    void updateTime(FightGameManager gameManager){
        timeLeftText.setText("" + gameManager.timeValue);
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

        ground.setLayoutX(0);
        ground.setLayoutY(350);
        ground.setFitWidth(600);
        ground.setFitHeight(50);

        background.setLayoutX(0);
        background.setLayoutY(0);
        background.setFitWidth(600);
        background.setFitHeight(400);

        aWidth = healthA.getWidth();
        bWidth = healthB.getWidth();

        gameLoop();
    }
}
