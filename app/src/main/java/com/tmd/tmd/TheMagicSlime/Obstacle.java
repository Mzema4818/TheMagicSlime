package com.tmd.tmd.TheMagicSlime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject{
    private Rect rectangle;
    private int color;
    private Bitmap picture;

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top += y/1.5;
        rectangle.bottom += y/1.5;
    }

    public Obstacle(int rectHeight, int color ,  int startX, int startY, int playerGap, String pic) {
        this.color = color;
        rectangle = new Rect(startX, startY ,startX + 200  , startY + rectHeight);
        int resourceIdentifier = Constants.CURRENT_CONTEXT.getResources().getIdentifier(pic, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        picture = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), resourceIdentifier);

    }

    public void newPicture(String pic){
        int resourceIdentifier = Constants.CURRENT_CONTEXT.getResources().getIdentifier(pic, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        picture = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), resourceIdentifier);
    }

    public int getMid(){
        return (int)rectangle.centerX();
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle()) && player.getRectangle().bottom < rectangle.bottom;
    }
    public Bitmap getPicture(){
        return picture;
    }

    public int returnTop(){
        return rectangle.top;
    }
    public int returnBottom(){
        return rectangle.bottom;
    }
    public int returnLeft(){
        return rectangle.left;
    }
    public int returnRight(){
        return rectangle.right;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    @Override
    public void update(){

    }
}

