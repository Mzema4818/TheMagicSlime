package com.tmd.tmd.TheMagicSlime;

import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;

public class ObstacleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private String pic;
    private Rect rectangleRect;

    private long startTime;
    private long initTime;

    private int score = 0;

    public ObstacleManager(int playerGap , int obstacleGap , int obstacleHeight, int color, String pic){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        this.pic = pic;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public void setPicture(String pic){
        for(Obstacle ob : obstacles) {
            if(ob.returnBottom() < 0)
                ob.newPicture(pic);
        }
    }

    public Obstacle playerCollide(RectPlayer player){
        for(Obstacle ob : obstacles)
            if (ob.playerCollide(player)) {
                return ob;
            }
        return null;
    }

    private void populateObstacles(){
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while (currY < 0){
            int xStart = (int)(Math.random() *(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color, xStart  , currY, playerGap, pic));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){
        long elapesedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/1000.0))*Constants.SCREEN_HEIGHT/10000.0f;
        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapesedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT + 150){
            int xStart = (int)(Math.random() *(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight,color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap , playerGap, pic));
            obstacles.remove(obstacles.size() - 1);
            GameplayScene.score ++;
        }
    }
    public void draw(Canvas canvas){
        for(Obstacle ob : obstacles) {
            ob.draw(canvas);

            rectangleRect =  new Rect(ob.returnLeft() - 75,ob.returnTop() - 90 ,ob.returnRight() + 75 ,ob.returnBottom() + 60);
            canvas.drawBitmap(ob.getPicture(), null, rectangleRect, null);
        }
    }
}
