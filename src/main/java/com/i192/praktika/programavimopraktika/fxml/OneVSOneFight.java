package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Vector2d;
import com.i192.praktika.programavimopraktika.game.Fighter;
import com.i192.praktika.programavimopraktika.game.CharacterState;
import com.i192.praktika.programavimopraktika.game.FightGameManager;
import com.i192.praktika.programavimopraktika.graphics.SpriteSheet;
import com.i192.praktika.programavimopraktika.spritesheet.BoxTypes;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class OneVSOneFight implements Initialisable{

    private static final int FRAMES_PER_SECOND = 18;
    //private static final double SECONDS_PER_FRAME = 1.0 / FRAMES_PER_SECOND;
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

    ArrayList<Rectangle> rectangles = new ArrayList<>();

    public Pane rectsHold;
    int rectsUsed = 0;

    public void setPlayers(ConfiguredController A, ConfiguredController B){
        this.playerA = A;
        this.playerB = B;
    }

    public void setSelectedCharacters(Fighter A , Fighter B){
        this.selectedCharacterA = A;
        this.selectedCharacterB = B;
    }



    public void gameLoop(){

        AnimationTimer timer = new AnimationTimer() {

            FightGameManager gameManager = new FightGameManager().setFighters(selectedCharacterA, selectedCharacterB).setControllers(playerA, playerB);


            boolean b = setPips(gameManager);
            double lastTime = 0;


            @Override
            public void handle(long now) {

                double elapsedSeconds = (now - lastTime) / 1e9;

                // Check if enough time has passed to update the animation
                if (elapsedSeconds >= 1.0 / FRAMES_PER_SECOND) {

                    gameManager.update(now);

                    //display both characters
                    updateCharacterImages(gameManager.characterStateA, ivA);
                    updateCharacterImages(gameManager.characterStateB, ivB);



                    updateHealth(gameManager.characterStateA, gameManager.characterStateB);

                    updateTime(gameManager);
                    updatePips(gameManager);

                    //updateRectangles(gameManager.characterStateA, gameManager.characterStateB);

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

                    // Update lastTime for the next frame
                    lastTime = now;
                }


            }
        };




        timer.start();

    }
    void updateRectangles(CharacterState characterStateA, CharacterState characterStateB){
        rectsUsed = 0;
        addRects(characterStateA.rb.rootPosition, characterStateA.getFrame().colliderBoxes, BoxTypes.COLLISION_BOX.getColor(), characterStateA.isOnRight);
        addRects(characterStateA.rb.rootPosition, characterStateA.getFrame().hitBoxes, BoxTypes.HIT_BOX.getColor(), characterStateA.isOnRight);
        addRects(characterStateA.rb.rootPosition, characterStateA.getFrame().hurtBoxes, BoxTypes.HURT_BOX.getColor(), characterStateA.isOnRight);

        addRects(characterStateB.rb.rootPosition, characterStateB.getFrame().colliderBoxes, BoxTypes.COLLISION_BOX.getColor(), characterStateB.isOnRight);
        addRects(characterStateB.rb.rootPosition, characterStateB.getFrame().hitBoxes, BoxTypes.HIT_BOX.getColor(), characterStateB.isOnRight);
        addRects(characterStateB.rb.rootPosition, characterStateB.getFrame().hurtBoxes, BoxTypes.HURT_BOX.getColor(), characterStateB.isOnRight);
        hideRecrest();
    }

    void addRects(Vector2d v, Box[] bs, Color c, boolean flip){
        for(Box bb:bs){
            Box b = bb.mabyFlippedX(100, flip);
            addRect(c, b.topLeft.x + v.x, b.topLeft.y + v.y, b.bottomRight.x - b.topLeft.x, b.bottomRight.y - b.topLeft.y);
        }
    }

    void addRect(Color c, double x, double y, double width, double height){
        Rectangle r;

        if(rectsUsed < rectangles.size()){
            r = rectangles.get(rectsUsed);
            rectsUsed++;
        }
        else {
            rectangles.add(new Rectangle());
            r = rectangles.get(rectsUsed);
            rectsHold.getChildren().add(r);
            rectsUsed++;
        }

        r.setLayoutX(x);
        r.setLayoutY(y);
        r.setWidth(width);
        r.setHeight(height);
        r.setFill(c);
        r.setOpacity(0.3);
    }

    void hideRecrest(){
        Rectangle r;
        while(rectsUsed < rectangles.size()){
            r = rectangles.get(rectsUsed);
            rectsUsed++;
            r.setLayoutX(1000000000);
            r.setLayoutY(1000000000);
        }
    }



    boolean setPips(FightGameManager gameManager){
        Node pip = pipsA.getChildren().getFirst();
        int aCount = gameManager.characterStateA.pipsLeft;

        if(pipsA.getChildren().size() == gameManager.characterStateA.pipsLeft){
            updatePips(gameManager);
            return true;
        }
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

    void updateCharacterImages(CharacterState characterState, ImageView iv){
        int[] ii = characterState.currImage();

        Image image = characterState.character.animations[ii[0]].spriteSheet.getSprite(ii[1],0);
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


        //try {
            //ground.setImage(new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/gorund.png")));
            //background.setImage(new Image(new FileInputStream("src/main/resources/com/i192/praktika/programavimopraktika/images/backgorund.png")));
        //} catch (FileNotFoundException e) {
        //    throw new RuntimeException(e);
        //}
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
