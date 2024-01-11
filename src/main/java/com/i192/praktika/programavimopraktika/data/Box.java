package com.i192.praktika.programavimopraktika.data;

public class Box{
    public Vector2d topLeft;
    public Vector2d bottomRight;

    public Box(Vector2d topLeft, Vector2d bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    Box(String s){
        String[] ss = s.split("~");
        this.topLeft = new Vector2d(ss[0]);
        this.bottomRight = new Vector2d(ss[1]);
    }

    public Box mabyFlippedX(double axis, boolean ifTrue){
        if(ifTrue){
            Vector2d tl = new Vector2d(((bottomRight.x - axis) * -1) + axis, topLeft.y);
            Vector2d br = new Vector2d(((topLeft.x - axis) * -1) + axis,bottomRight.y);
            return new Box(tl, br);
        }
        return this;
    }


    @Override
    public String toString() {
        return topLeft.toString() + "~" + bottomRight.toString();
    }
}
