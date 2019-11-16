package com.tmd.tmd.TheMagicSlime;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameplayScene extends StartGameActivity implements Scene {
    public static int score = 0;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private ObstacleManager obstacleManager1;
    private StillManager stillManager;
    private Obstacle onPlatformObject;
    private boolean gameOver = false;
    private boolean onPlatform1 = false;
    private boolean onPlatform2 = false;
    private boolean onPlatform3 = false;
    private boolean starsShow = false;
    private SharedPreferences pref;
    private Bitmap gameover;
    private Bitmap bg;
    private Bitmap bs;
    private Bitmap bss;
    private Bitmap bs1;
    private Bitmap bs2;
    private Bitmap bs3;
    private Bitmap stars;
    private Bitmap scoreBg = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.blue_score);
    private RectF button;
    private RectF shadowButton;
    private RectF button2;
    private RectF shadowButton2;
    private int y = 0;
    private int y2 = -Constants.SCREEN_HEIGHT;
    private int y3 = -Constants.SCREEN_HEIGHT * 2;
    private int y4 = -Constants.SCREEN_HEIGHT - 100;
    private int y5 = -Constants.SCREEN_HEIGHT * 2 - 100;
    private int y6 = -Constants.SCREEN_HEIGHT * 3 - 100;
    private int y7 = -Constants.SCREEN_HEIGHT * 4 - 100;
    private int amountOfOvers = 0;
    private MediaPlayer intro;
    private MediaPlayer intro2;
    private MediaPlayer teleport;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final DatabaseReference myRef = database.getReference();
    final FirebaseUser user = mAuth.getCurrentUser();
    private String currentSlime = "";
    private Context context;
    private InterstitialAd mInterstitialAd;
    private AdRequest request;

    public GameplayScene(Context context) {
        //Starting objects
        player = new RectPlayer(new Rect(0, 0, 176, 176));
        button = new RectF(50, Constants.SCREEN_HEIGHT - 50, 250, Constants.SCREEN_HEIGHT - 150);
        shadowButton = new RectF(55, Constants.SCREEN_HEIGHT - 45, 255, Constants.SCREEN_HEIGHT - 145);
        button2 = new RectF(Constants.SCREEN_WIDTH - 50, Constants.SCREEN_HEIGHT - 50, Constants.SCREEN_WIDTH - 250, Constants.SCREEN_HEIGHT - 150);
        shadowButton2 = new RectF(Constants.SCREEN_WIDTH - 45, Constants.SCREEN_HEIGHT - 45, Constants.SCREEN_WIDTH - 245, Constants.SCREEN_HEIGHT - 145);
        stillManager = new StillManager(125, 500, 55, Color.LTGRAY);
        obstacleManager = new ObstacleManager(125, 400, 50, Color.LTGRAY, "clouds");
        obstacleManager1 = new ObstacleManager(125, 700, 50, Color.DKGRAY, "stormcloud");
        this.context = context;

        //ADS
        request = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(context);
        //ca-app-pub-3940256099942544/1033173712 test ads
        mInterstitialAd.setAdUnitId("ca-app-pub-5721136288760913/3155986116");
        mInterstitialAd.loadAd(request);

        pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        amountOfOvers = pref.getInt("amountOfOvers", -1);

        if (amountOfOvers == -1) {
            editor.putInt("amountOfOvers", 0);
        }

        editor.commit();

        //sounds
        teleport = MediaPlayer.create(context, R.raw.teleport);
        intro = MediaPlayer.create(context, R.raw.intro1);
        intro2 = MediaPlayer.create(context, R.raw.intro2);
        intro.start();
        intro2.start();
        intro2.pause();
        intro.setLooping(true);
        intro2.setLooping(true);

        //Pictures
        bg = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ground);
        bs = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sky1);
        bss = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sky_and_stars);
        bs1 = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.stars1);
        bs2 = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.stars2);
        bs3 = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.stars3);

        //score & end screen
        myRef.child("user/" + user.getUid() + "/currentSlime").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSlime = dataSnapshot.getValue(String.class);
                String endgame = "";
                String scorebar = "";

                if (currentSlime.contains("blue")) {
                    endgame = "blue_over";
                    scorebar = "blue_score";
                }
                if (currentSlime.contains("green")) {
                    endgame = "green_over";
                    scorebar = "green_score";
                }
                if (currentSlime.contains("pink")) {
                    endgame = "pink_over";
                    scorebar = "pink_score";
                }
                if (currentSlime.contains("red")) {
                    endgame = "red_over";
                    scorebar = "red_score";
                }
                if (currentSlime.contains("yellow")) {
                    endgame = "yellow_over";
                    scorebar = "yellow_score";
                }

                int resourceIdentifier1 = Constants.CURRENT_CONTEXT.getResources().getIdentifier(scorebar, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
                int resourceIdentifier2 = Constants.CURRENT_CONTEXT.getResources().getIdentifier(endgame, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
                scoreBg = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), resourceIdentifier1);
                gameover = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), resourceIdentifier2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //effects
        myRef.child("user/" + user.getUid() + "/currentEffect").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String myeffect = dataSnapshot.getValue(String.class);
                int resourceIdentifier = Constants.CURRENT_CONTEXT.getResources().getIdentifier(myeffect, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
                stars = new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), resourceIdentifier);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //ADS
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(request);
            }
        });
    }

    public void reset() {
        onPlatform1 = false;
        onPlatform2 = false;
        onPlatform3 = false;
        stillManager = new StillManager(125, 500, 55, Color.LTGRAY);
        obstacleManager.setPicture("clouds");
        obstacleManager1.setPicture("stormcloud");
        obstacleManager = new ObstacleManager(125, 400, 50, Color.LTGRAY, "clouds");
        obstacleManager1 = new ObstacleManager(125, 700, 50, Color.DKGRAY, "stormcloud");
        obstacleManager.setPicture("clouds");
        obstacleManager1.setPicture("stormcloud");
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 14);
        player.update(playerPoint);
        player.resetSpeed();
        score = 0;
        y = 0;
        y2 = -Constants.SCREEN_HEIGHT;
        y3 = -Constants.SCREEN_HEIGHT * 2;
        y4 = -Constants.SCREEN_HEIGHT - 100;
        y5 = -Constants.SCREEN_HEIGHT * 2 - 100;
        y6 = -Constants.SCREEN_HEIGHT * 3 - 100;
        y7 = -Constants.SCREEN_HEIGHT * 4 - 100;
        gameOver = false;
        intro2.pause();
        intro.start();
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && (onPlatform1 || onPlatform2 || onPlatform3)) {
                    onPlatform1 = false;
                    onPlatform2 = false;
                    onPlatform3 = false;
                    starsShow = true;
                    playerPoint.set((int) (event.getX()), (int) event.getY());
                    player.update(playerPoint);
                    if (teleport.isPlaying()) {
                        teleport.reset();
                        teleport = MediaPlayer.create(context, R.raw.teleport);
                        teleport.start();
                    }
                    teleport.start();
                }
                if (gameOver && event.getX() > 50 && event.getX() < 250 && event.getY() > Constants.SCREEN_HEIGHT - 150 && event.getY() < Constants.SCREEN_HEIGHT - 50) {
                    endActivity();
                }
                if (gameOver && event.getX() > Constants.SCREEN_WIDTH - 250 && event.getX() < Constants.SCREEN_WIDTH - 50 && event.getY() > Constants.SCREEN_HEIGHT - 150 && event.getY() < Constants.SCREEN_HEIGHT - 50) {
                    reset();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Rect gameOverRect = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + 200);
        Rect screenrectangle = new Rect(0, y, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y + 10);
        Rect screenrectangle2 = new Rect(0, y2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y2 + 10);
        Rect screenrectangle3 = new Rect(0, y3, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y3 + 10);
        Rect screenrectangle4 = new Rect(0, y4, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y4 + 10);
        Rect screenrectangle5 = new Rect(0, y5, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y5 + 10);
        Rect screenrectangle6 = new Rect(0, y6, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y6 + 10);
        Rect screenrectangle7 = new Rect(0, y7, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT + y7 + 10);
        Rect scoreRect = new Rect(0, 0, 700, 400);
        Rect starrectangle = new Rect(player.getLeft() + 150, player.getTop() - 80, player.getRight() + 75, player.getBottom() - 120);
        y += 5;
        y2 += 5;
        y3 += 5;

        canvas.drawBitmap(bs, null, screenrectangle3, null);
        canvas.drawBitmap(bs, null, screenrectangle2, null);
        canvas.drawBitmap(bg, null, screenrectangle, null);

        if (y3 > Constants.SCREEN_HEIGHT && score <= 125) {
            y3 = -Constants.SCREEN_HEIGHT;
        } else if (y2 > Constants.SCREEN_HEIGHT && score <= 125) {
            y2 = -Constants.SCREEN_HEIGHT;
        }

        if (score >= 125) {
            canvas.drawBitmap(bss, null, screenrectangle4, null);
            canvas.drawBitmap(bs1, null, screenrectangle5, null);
            canvas.drawBitmap(bs2, null, screenrectangle6, null);
            canvas.drawBitmap(bs3, null, screenrectangle7, null);
            y4 += 5;
            y5 += 5;
            y6 += 5;
            y7 += 5;
        }

        if (score >= 140) {
            obstacleManager.setPicture("goodalien");
            obstacleManager1.setPicture("badalien");
            intro.pause();
            intro2.start();
        }

        if (y5 > Constants.SCREEN_HEIGHT && score >= 125) {
            y5 = -Constants.SCREEN_HEIGHT * 2;
        } else if (y6 > Constants.SCREEN_HEIGHT && score >= 125) {
            y6 = -Constants.SCREEN_HEIGHT * 2;
        } else if (y7 > Constants.SCREEN_HEIGHT && score >= 125) {
            y7 = -Constants.SCREEN_HEIGHT * 2;
        }

        if (starsShow) {
            canvas.drawBitmap(stars, null, starrectangle, null);
        }

        Paint paint1 = new Paint();
        paint1.setTextSize(80);
        paint1.setColor(Color.BLACK);
        Paint paint2 = new Paint();
        paint2.setTextSize(100);
        paint2.setColor(Color.GRAY);
        Paint paint3 = new Paint();
        paint3.setTextSize(70);
        paint3.setColor(Color.BLACK);
        Paint paint4 = new Paint();
        paint4.setTextSize(50);
        paint4.setColor(Color.BLACK);
        paint4.setTextAlign(Paint.Align.CENTER);
        Paint paint5 = new Paint();
        paint5.setTextSize(100);
        paint5.setColor(Color.DKGRAY);

        canvas.drawBitmap(scoreBg, null, scoreRect, null);
        canvas.drawText(" " + score, 100, 80 + paint1.descent() - paint1.ascent(), paint1);
        obstacleManager.draw(canvas);
        obstacleManager1.draw(canvas);
        stillManager.draw(canvas);
        player.draw(canvas);

        if (gameOver) {
            canvas.drawBitmap(gameover, null, gameOverRect, null);
            canvas.drawRoundRect(shadowButton, 20, 20, paint5);
            canvas.drawRoundRect(button, 20, 20, paint2);
            canvas.drawText("Back", button.left * 3, (button.top - button.left) + button.left / 4, paint4);

            canvas.drawRoundRect(shadowButton2, 20, 20, paint5);
            canvas.drawRoundRect(button2, 20, 20, paint2);
            canvas.drawText("Restart", button2.left - (button.left * 2), (button2.top - button.left) + button.left / 4, paint4);
        }
    }

    @Override
    public void update() {
        pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        amountOfOvers = pref.getInt("amountOfOvers", -1);

        if (amountOfOvers >= 5) {
            editor.putInt("amountOfOvers", 0);
            editor.commit();

            showInterstitial();
        }

        if (!gameOver) {
            obstacleManager.update();
            obstacleManager1.update();
            stillManager.update();
            Obstacle o1 = obstacleManager.playerCollide(player);
            Obstacle o2 = obstacleManager1.playerCollide(player);
            Obstacle still = stillManager.playerCollide(player);

            //Obstacle 1
            if (o1 != null && !onPlatform1) {
                onPlatform1 = true;
                onPlatform2 = true;
                onPlatform3 = true;
                starsShow = false;
                onPlatformObject = o1;
            } else if (onPlatform1) {
                playerPoint = new Point(player.getX(), onPlatformObject.getRectangle().top - 52);
                player.update(playerPoint);
            } else if (!onPlatform1) {
                player.update();
            }
            //Obstacle still
            if (still != null && !onPlatform2) {
                onPlatform1 = true;
                onPlatform2 = true;
                onPlatform3 = true;
                starsShow = false;
                onPlatformObject = still;
            } else if (onPlatform2) {
                playerPoint = new Point(player.getX(), onPlatformObject.getRectangle().top - 52);
                player.update(playerPoint);
            } else if (!onPlatform2) {
                player.update();
            }
            //Obstacle 2
            if (o2 != null && !onPlatform3) {
                starsShow = false;
                playerPoint = new Point(player.getX(), o2.getRectangle().top - 52);
                player.update(playerPoint);
                gameOver = true;
                editor.putInt("amountOfOvers", amountOfOvers += 1);
                editor.commit();
            }
            //If off screen
            if ((player.getTop() >= Constants.SCREEN_HEIGHT) || (player.getTop() >= Constants.SCREEN_HEIGHT) && (onPlatform1 || onPlatform2 || onPlatform3)) {
                onPlatform1 = false;
                onPlatform2 = false;
                onPlatform3 = false;
                starsShow = false;
                gameOver = true;
                editor.putInt("amountOfOvers", amountOfOvers += 1);
                editor.commit();
            }

            if (gameOver) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final DatabaseReference myRef = database.getReference();
                final FirebaseUser user = mAuth.getCurrentUser();
                myRef.child("user/" + user.getUid() + "/score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int myscore = dataSnapshot.getValue(Integer.class);
                        myRef.child("user/" + user.getUid() + "/score").setValue(myscore + score);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
    }

    //ADS
    public void showInterstitial() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    doShowInterstitial();
                }
            });
        } else {
            doShowInterstitial();
        }
    }

    private void doShowInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else{
            Log.d("Work","Doesnt work");
        }
    }
}

