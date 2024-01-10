package com.i192.praktika.programavimopraktika.physics;

import com.i192.praktika.programavimopraktika.data.Box;
import com.i192.praktika.programavimopraktika.data.Vector2d;

public class Collisions {

    public static boolean collides(Box a, Box b, Vector2d originA, Vector2d originB){
        Vector2d topLeftA = Vector2d.sum(a.topLeft, originA);//x is max rightness for points in box a // y is max topness for points in box a
        Vector2d topLeftB = Vector2d.sum(b.topLeft, originB);
        Vector2d bottomRightA = Vector2d.sum(a.bottomRight, originA);
        Vector2d bottomRightB = Vector2d.sum(b.bottomRight, originB);//x is max leftness for points in box b // y is max bottomness for points in box b

        boolean horisontalOne = bottomRightA.x > topLeftB.x;//is the rightest point in a righter than the leftest point in b
        boolean horisontalTwo = topLeftA.x < bottomRightB.x;

        boolean verticalOne = bottomRightA.y < topLeftB.y;
        boolean verticalTwo = topLeftA.y > bottomRightB.y;

        return horisontalOne & horisontalTwo & verticalOne & verticalTwo;
    }

    public static Vector2d push(Box a, Box b, Vector2d originA, Vector2d originB){
        Vector2d topLeftA = Vector2d.sum(a.topLeft, originA);//x is max rightness for points in box a // y is max topness for points in box a
        Vector2d topLeftB = Vector2d.sum(b.topLeft, originB);
        Vector2d bottomRightA = Vector2d.sum(a.bottomRight, originA);
        Vector2d bottomRightB = Vector2d.sum(b.bottomRight, originB);//x is max leftness for points in box b // y is max bottomness for points in box b

        Vector2d[] dirs = new Vector2d[4];

        dirs[0] = new Vector2d(bottomRightA.x - topLeftB.x, 0);
        dirs[1] = new Vector2d(topLeftA.x - bottomRightB.x, 0);
        dirs[2] = new Vector2d(0,bottomRightA.y - topLeftB.y);
        dirs[3] = new Vector2d(0,topLeftA.y - bottomRightB.y);

        //find shortest
        int bestIndex = 0;
        double bestLength = Float.MAX_VALUE;
        for(int i = 0; i < 4; i++){
            double lengthCurr = dirs[i].length();
            if(lengthCurr < bestLength){
                bestLength = lengthCurr;
                bestIndex = i;
            }
        }

        return dirs[bestIndex];
    }
}
