package com.i192.praktika.programavimopraktika.fxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameStage implements Initialisable {
    public Pane game;
    public Pane background;
    public Rectangle ground;
    public Rectangle leftWall;
    public Rectangle rightWall;
    public StackPane root;
    @Override
    public void initialise() {

    }
}
