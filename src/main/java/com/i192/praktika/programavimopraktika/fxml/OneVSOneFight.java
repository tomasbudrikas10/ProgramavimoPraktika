package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.Characters;
import com.i192.praktika.programavimopraktika.MainApplication;
import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.game.Fighter;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import com.i192.praktika.programavimopraktika.game.FightGameManager;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

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

    public HBox pipsA;
    public HBox pipsB;

    public TitledPane winPane;

    public ImageView winImage;

    public void setPlayers(ConfiguredController A, ConfiguredController B){
        this.playerA = A;
        this.playerB = B;
    }

    public void setSelectedCharacters(Fighter A , Fighter B){
        this.selectedCharacterA = A;
        this.selectedCharacterB = B;
    }



    public void gameLoop(){
        //selectedCharacterA = Characters.getFighter(Characters.GIRL);
        //selectedCharacterB = Characters.getFighter(Characters.BOY);

        AnimationTimer timer = new AnimationTimer() {

            FightGameManager gameManager = new FightGameManager().setFighters(selectedCharacterA, selectedCharacterB).setControllers(playerA, playerB);

            SpriteSheet spriteSheet = new SpriteSheet("CircleFighterNew.png", 22, 22);

            boolean b = setPips(gameManager);

            @Override
            public void handle(long now) {
                gameManager.update(now);

                //display both characters
                updateCharacterImages(spriteSheet, gameManager.characterStateA, ivA);
                updateCharacterImages(spriteSheet, gameManager.characterStateB, ivB);



                updateHealth(gameManager.characterStateA, gameManager.characterStateB);

                updateTime(gameManager);
                updatePips(gameManager);

                if(gameManager.bWon){
                    fightOverAction(selectedCharacterB);
                    winImage.setImage(new Image( getClass().getResource("/com/i192/praktika/programavimopraktika/images/" + selectedCharacterB.character.getImageName()).toExternalForm()));
                    this.stop();
                }

                if(gameManager.aWon){
                    fightOverAction(selectedCharacterA);
                    winImage.setImage(new Image( getClass().getResource("/com/i192/praktika/programavimopraktika/images/" + selectedCharacterA.character.getImageName()).toExternalForm()));
                    this.stop();
                }
            }
        };


        timer.start();
    }

    boolean setPips(FightGameManager gameManager){
        Node pip = pipsA.getChildren().getFirst();
        int aCount = gameManager.characterStateA.pipsLeft;

        for(int i = 1; i < aCount; i++){

            pipsA.getChildren().add(makePip());
            pipsB.getChildren().add(makePip());

        }

        return true;
    }

    Rectangle makePip(){
        Rectangle pipOG = (Rectangle) pipsA.getChildren().getFirst();
        Rectangle rect = new Rectangle(pipOG.getWidth(),pipOG.getHeight(), pipOG.getFill());
        rect.setStroke(pipOG.getStroke());
        rect.setArcHeight(pipOG.getArcHeight());
        rect.setArcWidth(pipOG.getArcWidth());
        rect.setStrokeType(pipOG.getStrokeType());
        return rect;
    }

    void updateHealth(CharacterState a, CharacterState b){
        healthA.setWidth(aWidth/100 * a.health);
        healthB.setWidth(bWidth/100 * b.health);
    }

    public void toCharSelect(){
        try {
            CharacterSelect cs = SceneManager.getInstance().getLoader(Scenes.CHARACTER_SELECT).getController();
            cs.setPlayers(playerA, playerB);
            SceneManager.getInstance().setScene(Scenes.CHARACTER_SELECT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playAgain(){
        try {
            SceneManager.getInstance().setScene(Scenes.ONE_VS_ONE_FIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void toMainMenu(){
        try {
            SceneManager.getInstance().setScene(Scenes.MAIN_MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void updatePips(FightGameManager gameManager){
        for(int i = 0; i < pipsA.getChildren().size(); i++){
            pipsA.getChildren().get(i).setVisible(i<gameManager.characterStateA.pipsLeft);
            pipsB.getChildren().get(i).setVisible(i<gameManager.characterStateB.pipsLeft);
        }
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

    public void fightOverAction(Fighter winingCharacter){
        winPane.setVisible(true);
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

        aWidth = healthA.getWidth();
        bWidth = healthB.getWidth();

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

        winPane.setVisible(false);

        gameLoop();
    }
}
