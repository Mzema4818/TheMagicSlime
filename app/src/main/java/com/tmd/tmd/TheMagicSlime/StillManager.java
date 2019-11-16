package com.tmd.tmd.TheMagicSlime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class StillManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private Bitmap picture;
    private Rect rectangleRect;

    private long startTime;
    private long initTime;

    private int score = 0;

    public StillManager(int playerGap , int obstacleGap , int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        picture = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.clouds);
        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public Obstacle playerCollide(RectPlayer player){
        for(Obstacle ob : obstacles)
            if (ob.playerCollide(player)) {
                return ob;
            }
        return null;
    }

    private void populateObstacles(){
            obstacles.add(new Obstacle(obstacleHeight,color, Constants.SCREEN_WIDTH/2 - 50 , 3*Constants.SCREEN_HEIGHT/10, playerGap, "clouds"));
    }

    public void update(){
        long elapesedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/2000.0))*Constants.SCREEN_HEIGHT/10000.0f;
        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapesedTime);
        }
    }
    public void draw(Canvas canvas){
        for(Obstacle ob : obstacles) {
            ob.draw(canvas);

            rectangleRect =  new Rect(ob.returnLeft() - 75,ob.returnTop() - 75 ,ob.returnRight() + 75 ,ob.returnBottom() + 75);
            canvas.drawBitmap(picture, null, rectangleRect, null);
        }
    }
}
