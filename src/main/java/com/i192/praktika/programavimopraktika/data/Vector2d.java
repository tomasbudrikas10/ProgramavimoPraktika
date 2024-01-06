package com.i192.praktika.programavimopraktika.data;

import static java.lang.Math.sqrt;

public class Vector2d{

    public static final Vector2d ZERO = new Vector2d(0,0);
    public float x;
    public float y;

    public Vector2d(float x, float y){
        this.x = x;
        this.y = y;
    }
    Vector2d(String s){
        String[] ss = s.split(";");
        this.x = Float.parseFloat(ss[0]);
        this.y = Float.parseFloat(ss[1]);
    }

    public static Vector2d sum(Vector2d a, Vector2d b){
        return new Vector2d(a.x + b.x,a.y + b.y);
    }
    public void add(Vector2d plus){
        x = x + plus.x;
        y = y + plus.y;
    }

    public float length(){
        return (float) sqrt(x * x + y * y);
    }
    @Override
    public String toString() {
        return x + ";" + y;
    }
}
