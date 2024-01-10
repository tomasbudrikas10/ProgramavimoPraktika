package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Frame;
import com.i192.praktika.programavimopraktika.data.Vector2d;

public class Fighter {
    //will provide access to assets
    //animation images, frame data, sounds, default health, movement speed, ect....

    //should have some way to map inputs(or anything that is relevant to this, like isInAirState) to animations
    Animation[] animations;

    public Fighter(){
        Box oneBox = new Box(Vector2d.ZERO, new Vector2d(22,22));
        Box[] oneBoxArr = new Box[1];
        oneBoxArr[0] = oneBox;
        Frame oneFrame = new Frame(oneBoxArr,oneBoxArr,oneBoxArr,0,0);
        Frame[] oneFrameArr = new Frame[1];
        oneFrameArr[0] = oneFrame;
        Animation[] animationArr = new Animation[1];
        animationArr[0] = new Animation(oneFrameArr, "CircleFighter.png");
        this.animations = animationArr;
    }
}
