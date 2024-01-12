package com.i192.praktika.programavimopraktika.game;

import com.i192.praktika.programavimopraktika.controller.Inputs;
import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Frame;
import com.i192.praktika.programavimopraktika.data.Vector2d;

import java.util.HashMap;
import java.util.function.Function;

public class Fighter {
    //will provide access to assets
    //animation images, frame data, sounds, default health, movement speed, ect....
    public int defaultHealth = 100;

    //should have some way to map inputs(or anything that is relevant to this, like isInAirState) to animations
    Animation[] animations;

    public HashMap<Inputs, Integer>[] inputAnimationMap;
    public HashMap<Inputs, Function<CharacterState,Boolean>> inputDownBoolLamdaMap;
    public HashMap<Inputs, Function<CharacterState,Boolean>> inputUpBoolLamdaMap;

    public Fighter(){
        Box oneBox = new Box(Vector2d.ZERO, new Vector2d(22,22));
        Box[] oneBoxArr = new Box[1];
        oneBoxArr[0] = oneBox;

        Box[] noBoxArr = new Box[0];


        //Frame oneFrame = new Frame(oneBoxArr,oneBoxArr,oneBoxArr,0,0, new Vector2d(0,0));
        Frame[] idleFrames = new Frame[6];
        {
            idleFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,0, new Vector2d(0,0));
            idleFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,0, new Vector2d(0,0));
            idleFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,0, new Vector2d(0,0));
            idleFrames[3] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,3,0, new Vector2d(0,0));
            idleFrames[4] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,4,0, new Vector2d(0,0));
            idleFrames[5] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,5,0, new Vector2d(0,0));
        }

        Frame[] runForwardFrames = new Frame[4];
        {
            runForwardFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,1, new Vector2d(15,0));
            runForwardFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,1, new Vector2d( 8,0));
            runForwardFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,1, new Vector2d(9,0));
            runForwardFrames[3] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,3,1, new Vector2d( 10,0));
        }

        Frame[] walkBackFrames = new Frame[4];
        {
            walkBackFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,2, new Vector2d(-7,0));
            walkBackFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,2, new Vector2d( -4,0));
            walkBackFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,2, new Vector2d(-5,0));
            walkBackFrames[3] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,3,2, new Vector2d( -6,0));
        }


        Frame[] punchFrames = new Frame[4];
        {
            Box[] punchBoxArr = new Box[1];
            punchBoxArr[0] = new Box(new Vector2d(15,0), new Vector2d(25,15));

            punchFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,3, new Vector2d(-3,0));
            punchFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,3, new Vector2d(0,0));
            punchFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,3, new Vector2d(3,0));
            punchFrames[3] = new Frame(punchBoxArr,oneBoxArr,oneBoxArr,3,3, new Vector2d(0,0));
        }

        Frame[] kickFrames = new Frame[5];
        {
            Box[] kickBoxArr = new Box[1];
            kickBoxArr[0] = new Box(new Vector2d(10,15), new Vector2d(25,25));

            kickFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,4, new Vector2d(3,0));
            kickFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,4, new Vector2d(3,0));
            kickFrames[2] = new Frame(kickBoxArr,oneBoxArr,oneBoxArr,2,4, new Vector2d(0,0));
            kickFrames[3] = new Frame(kickBoxArr,oneBoxArr,oneBoxArr,3,4, new Vector2d(0,0));
            kickFrames[4] = new Frame(kickBoxArr,oneBoxArr,oneBoxArr,4,4, new Vector2d(0,0));
        }

        Frame[] hurtFrames = new Frame[3];
        {
            hurtFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,5, new Vector2d(0,0));
            hurtFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,5, new Vector2d(0,0));
            hurtFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,5, new Vector2d(0,0));
        }

        Frame[] tumblingFrames = new Frame[7];
        {
            tumblingFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,6, new Vector2d(0,0));
            tumblingFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,6, new Vector2d(0,0));
            tumblingFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,6, new Vector2d(0,0));
            tumblingFrames[3] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,3,6, new Vector2d(0,0));
            tumblingFrames[4] = new Frame(noBoxArr,noBoxArr,oneBoxArr,4,6, new Vector2d(0,0));
            tumblingFrames[5] = new Frame(noBoxArr,noBoxArr,oneBoxArr,5,6, new Vector2d(0,0));
            tumblingFrames[6] = new Frame(noBoxArr,noBoxArr,oneBoxArr,6,6, new Vector2d(0,0));
        }

        Frame[] jumpFrames = new Frame[4];
        {
            jumpFrames[0] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,0,7, Vector2d.ZERO);
            jumpFrames[1] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,1,7, Vector2d.ZERO, new Vector2d(0,-300));
            jumpFrames[2] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,2,7, Vector2d.ZERO);
            jumpFrames[3] = new Frame(noBoxArr,oneBoxArr,oneBoxArr,3,7, Vector2d.ZERO);
        }

        Animation[] animationArr = new Animation[8];
        {
            animationArr[0] = new Animation(idleFrames);
            animationArr[1] = new Animation(runForwardFrames);
            animationArr[2] = new Animation(walkBackFrames);
            animationArr[3] = new Animation(punchFrames);
            animationArr[4] = new Animation(kickFrames);
            animationArr[5] = new Animation(hurtFrames);
            animationArr[6] = new Animation(tumblingFrames);
            animationArr[7] = new Animation(jumpFrames);
        }
        this.inputAnimationMap = new HashMap[2];

        {
            this.inputAnimationMap[0] = new HashMap<Inputs, Integer>();
            this.inputAnimationMap[1] = new HashMap<Inputs, Integer>();

            inputAnimationMap[0].put(Inputs.UP, 7);
            inputAnimationMap[0].put(Inputs.DOWN, 0);
            inputAnimationMap[0].put(Inputs.LEFT, 2);
            inputAnimationMap[0].put(Inputs.RIGHT, 1);
            inputAnimationMap[0].put(Inputs.PUNCH, 3);
            inputAnimationMap[0].put(Inputs.KICK, 4);

            inputAnimationMap[1].put(Inputs.UP, 7);
            inputAnimationMap[1].put(Inputs.DOWN, 0);
            inputAnimationMap[1].put(Inputs.LEFT, 1);
            inputAnimationMap[1].put(Inputs.RIGHT, 2);
            inputAnimationMap[1].put(Inputs.PUNCH, 3);
            inputAnimationMap[1].put(Inputs.KICK, 4);
        }

        this.inputDownBoolLamdaMap = new HashMap<>();
        {
            inputDownBoolLamdaMap.put(Inputs.UP, characterState -> {
                boolean b = characterState.isGrounded && characterState.animation == 0;
                characterState.isGrounded = false;
                return b;
            });
            inputDownBoolLamdaMap.put(Inputs.DOWN, characterState -> false);
            inputDownBoolLamdaMap.put(Inputs.KICK, characterState -> characterState.animation == 0);
            inputDownBoolLamdaMap.put(Inputs.PUNCH, characterState -> characterState.animation == 0);
            inputDownBoolLamdaMap.put(Inputs.RIGHT, characterState -> {
                characterState.looping = Inputs.RIGHT;
                return characterState.animation == 0;
            });
            inputDownBoolLamdaMap.put(Inputs.LEFT, characterState -> {
                characterState.looping = Inputs.LEFT;
                return characterState.animation == 0;
            });

        }

        this.inputUpBoolLamdaMap = new HashMap<>();
        {
            inputUpBoolLamdaMap.put(Inputs.UP, characterState -> false);
            inputUpBoolLamdaMap.put(Inputs.DOWN, characterState -> false);
            inputUpBoolLamdaMap.put(Inputs.KICK, characterState -> false);
            inputUpBoolLamdaMap.put(Inputs.PUNCH, characterState -> false);
            inputUpBoolLamdaMap.put(Inputs.RIGHT, characterState -> {
                characterState.looping = null;
                return false;
            });
            inputUpBoolLamdaMap.put(Inputs.LEFT, characterState -> {
                characterState.looping = null;
                return false;
            });
        }







        this.animations = animationArr;
    }
}
