package com.i192.praktika.programavimopraktika.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasicCharacter extends Pane {
    private ImageView sprite;
    private Rectangle head;
    private Rectangle torso;
    private Rectangle leftArm;
    private Rectangle rightArm;
    private Rectangle leftLeg;
    private Rectangle rightLeg;
    public BasicCharacter(double x, double y, int width, int height, Image sprite) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.sprite = new ImageView(sprite);
        this.sprite.setFitWidth(this.getWidth());
        this.sprite.setFitHeight(this.getHeight());
        this.sprite.setPreserveRatio(false);
        this.head = new Rectangle();
        System.out.println(this.getWidth() * 0.5 + " " + this.getWidth());
        this.head.setWidth(this.getWidth() * 0.5);
        System.out.println(this.head.getWidth());
        this.head.setHeight(this.getHeight() * 0.2);
        this.head.setLayoutX(this.getWidth() * 0.5 - this.head.getWidth() * 0.5);
        this.head.setLayoutY(0);
        this.torso = new Rectangle();
        this.torso.setWidth(this.getWidth() * 0.7);
        this.torso.setHeight(this.getHeight() * 0.4);
        this.torso.setLayoutX(this.getWidth() * 0.5 - this.torso.getWidth() * 0.5);
        this.torso.setLayoutY(this.head.getHeight());
        this.leftArm = new Rectangle();
        this.leftArm.setWidth(this.getWidth() * 0.15);
        this.leftArm.setHeight(this.getHeight() * 0.4);
        this.leftArm.setLayoutX(this.torso.getLayoutX() - this.leftArm.getWidth());
        this.leftArm.setLayoutY(this.torso.getLayoutY());
        this.rightArm = new Rectangle();
        this.rightArm.setWidth(this.getWidth() * 0.15);
        this.rightArm.setHeight(this.getHeight() * 0.4);
        this.rightArm.setLayoutX(this.torso.getLayoutX() + this.torso.getWidth());
        this.rightArm.setLayoutY(this.torso.getLayoutY());
        this.leftLeg = new Rectangle();
        this.leftLeg.setWidth(this.getWidth() * 0.3);
        this.leftLeg.setHeight(this.getHeight() * 0.4);
        this.leftLeg.setLayoutX(this.torso.getLayoutX());
        this.leftLeg.setLayoutY(this.torso.getLayoutY() + this.torso.getHeight());
        this.rightLeg = new Rectangle();
        this.rightLeg.setWidth(this.getWidth() * 0.3);
        this.rightLeg.setHeight(this.getHeight() * 0.4);
        this.rightLeg.setLayoutX(this.torso.getLayoutX() + this.torso.getWidth() - this.rightLeg.getWidth());
        this.rightLeg.setLayoutY(this.torso.getLayoutY() + this.torso.getHeight());
        this.head.setFill(Color.GREEN);
        this.setBorder(Border.stroke(Color.BLACK));
        this.getChildren().addAll(head, torso, leftLeg, rightLeg, leftArm, rightArm);
    }
}
