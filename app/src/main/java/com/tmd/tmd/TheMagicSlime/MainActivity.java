package com.tmd.tmd.TheMagicSlime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference();
    SharedPreferences pref;
    boolean showToast;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showToast = true;
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);

        if((username == null) && (password == null)){
            Intent i = new Intent(MainActivity.this,Main6Activity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();

        //Toast
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        CharSequence text = "Currently logged in as " + username;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.START, 0, 0);

        if(!(username == null)) {
            Signin();
            if(showToast){
                toast.show();
            }
        }

    }

    public void Signin(){
        String email = pref.getString("username", null);
        String pwd = pref.getString("password", null);

        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                        }
                        // ...
                    }
                });
    }

    public void playView(View v){
        Intent i = new Intent(MainActivity.this,StartGameActivity.class);
        startActivity(i);
    }
    public void playView2(View v){
        finish();
        Intent i = new Intent(MainActivity.this,Main3Activity.class);
        startActivity(i);
    }
    public void playView3(View v){
        finish();
        Intent i = new Intent(MainActivity.this,Main4Activity.class);
        startActivity(i);
    }
}