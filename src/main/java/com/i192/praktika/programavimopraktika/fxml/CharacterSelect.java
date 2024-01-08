package com.i192.praktika.programavimopraktika.fxml;

import com.i192.praktika.programavimopraktika.Characters;
import com.i192.praktika.programavimopraktika.SceneManager;
import com.i192.praktika.programavimopraktika.Scenes;
import com.i192.praktika.programavimopraktika.controller.ConfiguredController;
import com.i192.praktika.programavimopraktika.controller.ControllerManager;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterSelect implements Initialisable{

    @FXML
    public GridPane characters;

    //toDo: pass in the controllers from the previous scene...
    //toDo: have some characters to select...
    public ConfiguredController playerA = null;
    public ConfiguredController playerB = null;

    public void setPlayers(ConfiguredController playerA , ConfiguredController playerB){
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public void charSelectLoop(){

        boolean characters_selected = false;

        AnimationTimer timer = new AnimationTimer() {


            //toDo:display each joined player



            @Override
            public void handle(long now) {

                playerA.updateLatestChanges();
                playerB.updateLatestChanges();

                //toDO: respond to changed inputs

                try {
                    OneVSOneFight ovof = SceneManager.getInstance().getLoader(Scenes.ONE_VS_ONE_FIGHT).getController();
                    ovof.setPlayers(playerA, playerB);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    charactersSelectedAction();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                this.stop();
                if(characters_selected){

                }


            }

        };

        timer.start();
    }

    public void charactersSelectedAction() throws IOException {
        SceneManager.getInstance().setScene(Scenes.ONE_VS_ONE_FIGHT);
    }

    @Override
    public void initialise() {
//        charSelectLoop();
        int characterCount = 0;
        for (Characters character : Characters.values()) {
            StackPane stackPane = new StackPane();
            Pane pane = new Pane();
            pane.setBackground(Background.fill(new ImagePattern(new Image(getClass().getResource("/com/i192/praktika/programavimopraktika/images/" + character.getImageName()).toExternalForm()))));
            stackPane.getChildren().add(pane);
            VBox vbox = new VBox();
            vbox.setBackground(Background.fill(Color.rgb(128, 128, 128, 0.7)));
            Text name = new Text(character.getName());
            Text description = new Text(character.getDescription());
            vbox.getChildren().addAll(name, description);
            name.setFont(Font.font(28));
            name.setFill(Paint.valueOf("white"));
            description.setFill(Paint.valueOf("white"));
            description.setFont(Font.font(16));
            vbox.setSpacing(15);
            vbox.setAlignment(Pos.CENTER);
            vbox.setOpacity(0);
            stackPane.getChildren().add(vbox);
            vbox.setOnMouseEntered(e -> {
                vbox.setOpacity(1);
            });
            vbox.setOnMouseExited(e -> {
                vbox.setOpacity(0);
            });
            characters.add(stackPane, characterCount % characters.getColumnCount(), characterCount / (characters.getColumnCount()));
            characterCount++;
        }
    }
}
