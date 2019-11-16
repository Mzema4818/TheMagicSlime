package com.tmd.tmd.TheMagicSlime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final DatabaseReference myRef = database.getReference();
    final FirebaseUser user = mAuth.getCurrentUser();
    int myscore;
    String currentSlime = "";
    String currentEffect = "";
    ArrayList <String> outfits;
    TextView score = null;

    Button blue = null;
    Button green = null;
    Button pink = null;
    Button red = null;
    Button yellow = null;

    Button wand1 = null;
    Button wand2 = null;
    Button wand3 = null;
    Button wand4 = null;
    Button wand5 = null;

    Button effect1 = null;
    Button effect2 = null;
    Button effect3 = null;
    Button effect4 = null;
    Button effect5 = null;

    Button hat1 = null;
    Button hat2 = null;
    Button hat3 = null;
    Button hat4 = null;
    Button hat5 = null;
    Button hat6 = null;
    Button hat7 = null;
    Button hat8 = null;
    Button hat9 = null;

    String slime = "";
    String hat = "";
    String wand = "";
    String effect = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        outfits = new ArrayList<>();

        score = findViewById(R.id.lblscore);

        blue = findViewById(R.id.blue);
        green = findViewById(R.id.green);
        pink = findViewById(R.id.pink);
        red = findViewById(R.id.red);
        yellow = findViewById(R.id.yellow);

        wand1 = findViewById(R.id.wand1);
        wand2 = findViewById(R.id.wand2);
        wand3 = findViewById(R.id.wand3);
        wand4 = findViewById(R.id.wand4);
        wand5 = findViewById(R.id.wand5);

        effect1 = findViewById(R.id.effect1);
        effect2 = findViewById(R.id.effect2);
        effect3 = findViewById(R.id.effect3);
        effect4 = findViewById(R.id.effect4);
        effect5 = findViewById(R.id.effect5);

        hat1 = findViewById(R.id.hat1);
        hat2 = findViewById(R.id.hat2);
        hat3 = findViewById(R.id.hat3);
        hat4 = findViewById(R.id.hat4);
        hat5 = findViewById(R.id.hat5);
        hat6 = findViewById(R.id.hat6);
        hat7 = findViewById(R.id.hat7);
        hat8 = findViewById(R.id.hat8);
        hat9 = findViewById(R.id.hat9);

        //Gets users score
        myRef.child("user/" + user.getUid() + "/score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myscore = dataSnapshot.getValue(Integer.class);
                score.setText("Money: " + myscore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }});

        //goes through all outfits bought
        myRef.child("user/" + user.getUid() + "/purchasedOutfits").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList< String>> objectsGTypeInd = new GenericTypeIndicator<ArrayList< String>>() {};
                outfits = dataSnapshot.getValue(objectsGTypeInd);

                for(String outfit : outfits){
                    if(outfit.contains("green")){
                        green.setText("Select");
                    }if(outfit.contains("pink")){
                        pink.setText("Select");
                    }if(outfit.contains("red")){
                        red.setText("Select");
                    }if(outfit.contains("yellow")){
                        yellow.setText("Select");
                    }if(outfit.contains("wand1")){
                        wand1.setText("Select");
                    }if(outfit.contains("wand2")){
                        wand2.setText("Select");
                    }if(outfit.contains("wand3")){
                        wand3.setText("Select");
                    }if(outfit.contains("wand4")){
                        wand4.setText("Select");
                    }if(outfit.contains("wand5")){
                        wand5.setText("Select");
                    }if(outfit.contains("lighting")){
                        effect2.setText("Select");
                    }if(outfit.contains("flames")){
                        effect3.setText("Select");
                    }if(outfit.contains("eyes")){
                        effect4.setText("Select");
                    }if(outfit.contains("face")){
                        effect5.setText("Select");
                    }if(outfit.contains("hat1")){
                        hat1.setText("Select");
                    }if(outfit.contains("hat2")){
                        hat2.setText("Select");
                    }if(outfit.contains("hat3")){
                        hat3.setText("Select");
                    }if(outfit.contains("hat4")){
                        hat4.setText("Select");
                    }if(outfit.contains("hat5")){
                        hat5.setText("Select");
                    }if(outfit.contains("hat6")){
                        hat6.setText("Select");
                    }if(outfit.contains("hat7")){
                        hat7.setText("Select");
                    }if(outfit.contains("hat8")){
                        hat8.setText("Select");
                    }if(outfit.contains("hat9")){
                        hat9.setText("Select");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }});

        //picks whats selected
        myRef.child("user/" + user.getUid() + "/currentSlime").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentSlime = dataSnapshot.getValue(String.class);

                if(currentSlime.contains("blue")){
                    slime = "blue";
                    blue.setText("Selected");
                }if(currentSlime.contains("green")){
                    slime = "green";
                    green.setText("Selected");
                }if(currentSlime.contains("pink")){
                    slime = "pink";
                    pink.setText("Selected");
                }if(currentSlime.contains("red")){
                    slime = "red";
                    red.setText("Selected");
                }if(currentSlime.contains("yellow")){
                    slime = "yellow";
                    yellow.setText("Selected");
                }if(currentSlime.contains("wand1")){
                    wand = "wand1";
                    wand1.setText("Selected");
                }if(currentSlime.contains("wand2")){
                    wand = "wand2";
                    wand2.setText("Selected");
                }if(currentSlime.contains("wand3")){
                    wand = "wand3";
                    wand3.setText("Selected");
                }if(currentSlime.contains("wand4")){
                    wand = "wand4";
                    wand4.setText("Selected");
                }if(currentSlime.contains("wand5")){
                    wand = "wand5";
                    wand5.setText("Selected");
                }if(currentSlime.contains("hat1")){
                    hat = "hat1";
                    hat1.setText("Selected");
                }if(currentSlime.contains("hat2")){
                    hat = "hat2";
                    hat2.setText("Selected");
                }if(currentSlime.contains("hat3")){
                    hat = "hat3";
                    hat3.setText("Selected");
                }if(currentSlime.contains("hat4")){
                    hat = "hat4";
                    hat4.setText("Selected");
                }if(currentSlime.contains("hat5")){
                    hat = "hat5";
                    hat5.setText("Selected");
                }if(currentSlime.contains("hat6")){
                    hat = "hat6";
                    hat6.setText("Selected");
                }if(currentSlime.contains("hat7")){
                    hat = "hat7";
                    hat7.setText("Selected");
                }if(currentSlime.contains("hat8")){
                    hat = "hat8";
                    hat8.setText("Selected");
                }if(currentSlime.contains("hat9")){
                    hat = "hat9";
                    hat9.setText("Selected");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }});

        myRef.child("user/" + user.getUid() + "/currentEffect").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentEffect = dataSnapshot.getValue(String.class);

                if(currentEffect.contains("stars")){
                    effect = "stars";
                    effect1.setText("Selected");
                }if(currentEffect.contains("lighting")){
                    effect = "lighting";
                    effect2.setText("Selected");
                }if(currentEffect.contains("flames")){
                    effect = "flames";
                    effect3.setText("Selected");
                }if(currentEffect.contains("eyes")){
                    effect = "eyes";
                    effect4.setText("Selected");
                }if(currentEffect.contains("face")){
                    effect = "face";
                    effect5.setText("Selected");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }});
    }

    //Sends to a different activity
    public void playView(View v){
        finish();
        Intent i = new Intent(Main4Activity.this,MainActivity.class);
        startActivity(i);
    }

    //puts all slime buttons back to select
    public void terminateSlimes(){
        if(blue.getText().equals("Selected")){
            blue.setText("Select");
        }if(green.getText().equals("Selected")){
            green.setText("Select");
        }if(pink.getText().equals("Selected")){
            pink.setText("Select");
        }if(red.getText().equals("Selected")){
            red.setText("Select");
        }if(yellow.getText().equals("Selected")){
            yellow.setText("Select");
        }
    }
    //puts all wands buttons back to select
    public void terminateWands(){
        if(wand1.getText().equals("Selected")){
            wand1.setText("Select");
        }if(wand2.getText().equals("Selected")){
            wand2.setText("Select");
        }if(wand3.getText().equals("Selected")){
            wand3.setText("Select");
        }if(wand4.getText().equals("Selected")){
            wand4.setText("Select");
        }if(wand5.getText().equals("Selected")){
            wand5.setText("Select");
        }
    }
    //puts all effects buttons back to select
    public void terminateEffects(){
        if(effect1.getText().equals("Selected")){
            effect1.setText("Select");
        }if(effect2.getText().equals("Selected")){
            effect2.setText("Select");
        }if(effect3.getText().equals("Selected")){
            effect3.setText("Select");
        }if(effect4.getText().equals("Selected")){
            effect4.setText("Select");
        }if(effect5.getText().equals("Selected")){
            effect5.setText("Select");
        }
    }
    //puts all hats buttons back to select
    public void terminateHats(){
        if(hat1.getText().equals("Selected")){
            hat1.setText("Select");
        }if(hat2.getText().equals("Selected")){
            hat2.setText("Select");
        }if(hat3.getText().equals("Selected")){
            hat3.setText("Select");
        }if(hat4.getText().equals("Selected")){
            hat4.setText("Select");
        }if(hat5.getText().equals("Selected")){
            hat5.setText("Select");
        }if(hat6.getText().equals("Selected")){
            hat6.setText("Select");
        }if(hat7.getText().equals("Selected")){
            hat7.setText("Select");
        }if(hat8.getText().equals("Selected")){
            hat8.setText("Select");
        }if(hat9.getText().equals("Selected")){
            hat9.setText("Select");
        }
    }

    //blue slime
    public void blue(View v) {
        terminateSlimes();
        if(blue.getText().equals("Select")){
            slime = "blue";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            blue.setText("Selected");
        }
    }
    //green slime
    public void green(View v){
        terminateSlimes();
        if(green.getText().equals("Select")){
            slime = "green";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            green.setText("Selected");
        }
        else if(myscore >= 10 && !outfits.contains("green")){
            green.setText("Select");
            outfits.add("green");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 10);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 10;
            score.setText("Money: " + myscore);
        }
    }
    //pink slime
    public void pink(View v) {
        terminateSlimes();
        if(pink.getText().equals("Select")){
            slime = "pink";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            pink.setText("Selected");
        }
        else if (myscore >= 50 && !outfits.contains("pink")) {
            pink.setText("Select");
            outfits.add("pink");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 50);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 50;
            score.setText("Money: " + myscore);
        }
    }
    //red slime
    public void red(View v){
        terminateSlimes();
        if(red.getText().equals("Select")){
            slime = "red";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            red.setText("Selected");
        }
        else if(myscore >= 75 && !outfits.contains("red")){
            red.setText("Select");
            outfits.add("red");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 75);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 75;
            score.setText("Money: " + myscore);
        }
    }
    //yellow slime
    public void yellow(View v){
        terminateSlimes();
        if(yellow.getText().equals("Select")){
            slime = "yellow";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            yellow.setText("Selected");
        }
        else if(myscore >= 100 && !outfits.contains("yellow")){
            yellow.setText("Select");
            outfits.add("yellow");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 100);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 100;
            score.setText("Money: " + myscore);
        }
    }

    //no wand
    public void noWand(View v){
        terminateWands();
        wand = "";
        myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
    }
    //wand 1
    public void wand1(View v){
        terminateWands();
        if(wand1.getText().equals("Select")){
            wand = "wand1";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            wand1.setText("Selected");
        }
        else if(myscore >= 50 && !outfits.contains("wand1")){
            wand1.setText("Select");
            outfits.add("wand1");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 50);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 50;
            score.setText("Money: " + myscore);
        }
    }
    //wand 2
    public void wand2(View v){
        terminateWands();
        if(wand2.getText().equals("Select")){
            wand = "wand2";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            wand2.setText("Selected");
        }
        else if(myscore >= 100 && !outfits.contains("wand2")){
            wand2.setText("Select");
            outfits.add("wand2");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 100);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 100;
            score.setText("Money: " + myscore);
        }
    }
    //wand 3
    public void wand3(View v){
        terminateWands();
        if(wand3.getText().equals("Select")){
            wand = "wand3";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            wand3.setText("Selected");
        }
        else if(myscore >= 200 && !outfits.contains("wand3")){
            wand3.setText("Select");
            outfits.add("wand3");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 200);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 200;
            score.setText("Money: " + myscore);
        }
    }
    //wand 4
    public void wand4(View v){
        terminateWands();
        if(wand4.getText().equals("Select")){
            wand = "wand4";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            wand4.setText("Selected");
        }
        else if(myscore >= 450 && !outfits.contains("wand4")){
            wand4.setText("Select");
            outfits.add("wand4");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 450);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 450;
            score.setText("Money: " + myscore);
        }
    }
    //wand 5
    public void wand5(View v){
        terminateWands();
        if(wand5.getText().equals("Select")){
            wand = "wand5";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            wand5.setText("Selected");
        }
        else if(myscore >= 750 && !outfits.contains("wand5")){
            wand5.setText("Select");
            outfits.add("wand5");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 750);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 750;
            score.setText("Money: " + myscore);
        }
    }

    //stars
    public void stars(View v) {
        terminateEffects();
        if(effect1.getText().equals("Select")){
            effect = "stars";
            myRef.child("user/" + user.getUid() + "/currentEffect").setValue(effect);
            effect1.setText("Selected");
        }
    }
    //lighting
    public void lighting(View v){
        terminateEffects();
        if(effect2.getText().equals("Select")){
            effect = "lighting";
            myRef.child("user/" + user.getUid() + "/currentEffect").setValue(effect);
            effect2.setText("Selected");
        }
        else if(myscore >= 250 && !outfits.contains("lighting")){
            effect2.setText("Select");
            outfits.add("lighting");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 250);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 250;
            score.setText("Money: " + myscore);
        }
    }
    //flames
    public void flames(View v){
        terminateEffects();
        if(effect3.getText().equals("Select")){
            effect = "flames";
            myRef.child("user/" + user.getUid() + "/currentEffect").setValue(effect);
            effect3.setText("Selected");
        }
        else if(myscore >= 500 && !outfits.contains("flames")){
            effect3.setText("Select");
            outfits.add("flames");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 500);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 500;
            score.setText("Money: " + myscore);
        }
    }
    //eyes
    public void eyes(View v){
        terminateEffects();
        if(effect4.getText().equals("Select")){
            effect = "eyes";
            myRef.child("user/" + user.getUid() + "/currentEffect").setValue(effect);
            effect4.setText("Selected");
        }
        else if(myscore >= 750 && !outfits.contains("eyes")){
            effect4.setText("Select");
            outfits.add("eyes");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 750);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 750;
            score.setText("Money: " + myscore);
        }
    }
    //face
    public void face(View v){
        terminateEffects();
        if(effect5.getText().equals("Select")){
            effect = "face";
            myRef.child("user/" + user.getUid() + "/currentEffect").setValue(effect);
            effect5.setText("Selected");
        }
        else if(myscore >= 750 && !outfits.contains("face")){
            effect5.setText("Select");
            outfits.add("face");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 750);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 750;
            score.setText("Money: " + myscore);
        }
    }

    //no hat
    public void noHat(View v){
        terminateHats();
        hat = "";
        myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
    }
    //hat 1
    public void hat1(View v){
        terminateHats();
        if(hat1.getText().equals("Select")){
            hat = "hat1";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat1.setText("Selected");
        }
        else if(myscore >= 25 && !outfits.contains("hat1")){
            hat1.setText("Select");
            outfits.add("hat1");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 25);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 25;
            score.setText("Money: " + myscore);
        }
    }
    //hat 2
    public void hat2(View v){
        terminateHats();
        if(hat2.getText().equals("Select")){
            hat = "hat2";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat2.setText("Selected");
        }
        else if(myscore >= 50 && !outfits.contains("hat2")){
            hat2.setText("Select");
            outfits.add("hat2");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 50);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 50;
            score.setText("Money: " + myscore);
        }
    }
    //hat 3
    public void hat3(View v){
        terminateHats();
        if(hat3.getText().equals("Select")){
            hat = "hat3";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat3.setText("Selected");
        }
        else if(myscore >= 125 && !outfits.contains("hat3")){
            hat3.setText("Select");
            outfits.add("hat3");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 125);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 125;
            score.setText("Money: " + myscore);
        }
    }
    //hat 4
    public void hat4(View v){
        terminateHats();
        if(hat4.getText().equals("Select")){
            hat = "hat4";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat4.setText("Selected");
        }
        else if(myscore >= 250 && !outfits.contains("hat4")){
            hat4.setText("Select");
            outfits.add("hat4");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 250);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 250;
            score.setText("Money: " + myscore);
        }
    }
    //hat 5
    public void hat5(View v){
        terminateHats();
        if(hat5.getText().equals("Select")){
            hat = "hat5";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat5.setText("Selected");
        }
        else if(myscore >= 350 && !outfits.contains("hat5")){
            hat5.setText("Select");
            outfits.add("hat5");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 350);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 350;
            score.setText("Money: " + myscore);
        }
    }
    //hat 6
    public void hat6(View v){
        terminateHats();
        if(hat6.getText().equals("Select")){
            hat = "hat6";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat6.setText("Selected");
        }
        else if(myscore >= 500 && !outfits.contains("hat6")){
            hat6.setText("Select");
            outfits.add("hat6");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 500);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 500;
            score.setText("Money: " + myscore);
        }
    }
    //hat 7
    public void hat7(View v){
        terminateHats();
        if(hat7.getText().equals("Select")){
            hat = "hat7";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat7.setText("Selected");
        }
        else if(myscore >= 750 && !outfits.contains("hat7")){
            hat7.setText("Select");
            outfits.add("hat7");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 750);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 750;
            score.setText("Money: " + myscore);
        }
    }
    //hat 8
    public void hat8(View v){
        terminateHats();
        if(hat8.getText().equals("Select")){
            hat = "hat8";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat8.setText("Selected");
        }
        else if(myscore >= 900 && !outfits.contains("hat8")){
            hat8.setText("Select");
            outfits.add("hat8");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 900);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 900;
            score.setText("Money: " + myscore);
        }
    }
    //hat 9
    public void hat9(View v){
        terminateHats();
        if(hat9.getText().equals("Select")){
            hat = "hat9";
            myRef.child("user/" + user.getUid() + "/currentSlime").setValue(slime + hat + wand);
            hat9.setText("Selected");
        }
        else if(myscore >= 1500 && !outfits.contains("hat9")){
            hat9.setText("Select");
            outfits.add("hat9");
            myRef.child("user/" + user.getUid() + "/score").setValue(myscore - 1500);
            myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(outfits);
            myscore -= 1500;
            score.setText("Money: " + myscore);
        }
    }
}
