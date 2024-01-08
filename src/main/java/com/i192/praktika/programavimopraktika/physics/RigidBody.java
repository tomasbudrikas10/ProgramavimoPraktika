package com.i192.praktika.programavimopraktika.physics;

import com.i192.praktika.programavimopraktika.data.Vector2d;

public class RigidBody {
    public Vector2d rootPosition;
    public Vector2d velocity = new Vector2d(0,0);

    public RigidBody(Vector2d startPosition){
        this.rootPosition = startPosition;
    }
    public void move(float fractionOfVelocity){
        rootPosition = new Vector2d( rootPosition.x + velocity.x * fractionOfVelocity, rootPosition.y + velocity.y * fractionOfVelocity);
    }
}

