package com.i192.praktika.programavimopraktika.data;

import static java.lang.Math.sqrt;

public class Vector2d{

    public static final Vector2d ZERO = new Vector2d(0,0);
    public double x;
    public double y;

    public Vector2d(double x, double y){
        this.x = x;
        this.y = y;
    }
    Vector2d(String s){
        String[] ss = s.split(";");
        this.x = Double.parseDouble(ss[0]);
        this.y = Double.parseDouble(ss[1]);
    }

    public static Vector2d sum(Vector2d a, Vector2d b){
        return new Vector2d(a.x + b.x,a.y + b.y);
    }
    public static Vector2d mul(Vector2d a, double b){
        return new Vector2d(a.x * b,a.y * b);
    }
    public static Vector2d flipX(Vector2d a){
        return new Vector2d(a.x * -1,a.y);
    }
    public void add(Vector2d plus){
        x = x + plus.x;
        y = y + plus.y;
    }

    public Vector2d noPosY(){
        if(y>0){
            setY(0);
        }
        return this;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double length(){
        return (double) sqrt(x * x + y * y);
    }
    @Override
    public String toString() {
        return x + ";" + y;
    }
}
