package com.tmd.tmd.TheMagicSlime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RectPlayer implements GameObject{

    private Point cord;
    private Rect rectangle;
    private int increase = 20;

    private Animation idle;
    private AnimationManager animManager;

    private long currentTime;
    private boolean gotTime = false;
    private long initTime;
    private String currentSlime = "";
    private Bitmap idleImg = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.blue);

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference myRef = database.getReference();
    private final FirebaseUser user = mAuth.getCurrentUser();

    public Rect getRectangle(){
        return rectangle;
    }

    public void resetSpeed(){
        increase = 20;
        gotTime = false;
    }

    public RectPlayer(Rect rectangle){
        this.rectangle = rectangle;
        this.cord = new Point(0,0);

        //Get Current Slime
        myRef.child("user/" + user.getUid() + "/currentSlime").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSlime = dataSnapshot.getValue(String.class);
                int resourceIdentifier = Constants.CURRENT_CONTEXT.getResources().getIdentifier(currentSlime, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
                idleImg = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), resourceIdentifier);

                idle = new Animation(new Bitmap[]{idleImg},2);

                Matrix m = new Matrix();
                m.preScale(-1, 1);

                animManager = new AnimationManager(new Animation[]{idle});
                update(new Point(Constants.SCREEN_WIDTH/2 , 3*Constants.SCREEN_HEIGHT/14));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    public int getX(){
        return cord.x;
    }
    public int getLeft(){
        return rectangle.left;
    }

    public int getTop(){
        return rectangle.top;
    }

    public int getBottom(){
        return rectangle.bottom;
    }

    public int getRight(){
        return rectangle.right;
    }

    @Override
    public void draw(Canvas canvas){
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update(){
        cord.y += increase;
        rectangle.set(cord.x - 88,cord.y - 88, cord.x + 88,cord.y + 88);
        if(!gotTime){
            initTime = System.currentTimeMillis();
            gotTime = true;
        }
        currentTime = System.currentTimeMillis();
        if(currentTime >= initTime + 20000){
            increase += 5;
            gotTime = false;
        }
        //animManager.playAnim(0);
        //animManager.update();
    }

    public void update(Point point){
        cord = point;
        rectangle.set(cord.x - 88,cord.y - 88, cord.x + 88,cord.y + 88);
        animManager.playAnim(0);
    }
}
