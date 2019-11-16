package com.tmd.tmd.TheMagicSlime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class Main6Activity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference();

    public void playView(View v){
        Intent i = new Intent(Main6Activity.this,MainActivity.class);
        startActivity(i);
    }

    public void CreateAccount(final View v){
        final EditText email = findViewById(R.id.edtEmail);
        final EditText pwd = findViewById(R.id.edtPassword);

        try{
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = pref.edit();

                                editor.putString("username", email.getText().toString());
                                editor.putString("password", pwd.getText().toString());
                                editor.commit();

                                startingAssets();
                                playView(v);
                                Signin(v);
                            } else {
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_LONG;
                                CharSequence text = "Email already exists";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.setGravity(Gravity.TOP|Gravity.START, 0, 0);
                                toast.show();
                            }
                        }
                    });
        }catch (Exception e) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            CharSequence text = "Incorrect, please try again";
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP|Gravity.START, 0, 0);
            toast.show();
        }
    }
    public void Signin(View v){
        final EditText email = findViewById(R.id.edtEmail);
        EditText pwd = findViewById(R.id.edtPassword);

        mAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_LONG;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            String em =  pref.getString("username", null);
                            CharSequence text = "Successfully made account " + em;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.setGravity(Gravity.TOP|Gravity.START, 0, 0);
                            toast.show();
                        }

                        // ...
                    }
                });
    }

    public void startingAssets(){
        FirebaseUser user = mAuth.getCurrentUser();
        myRef.child("user/" + user.getUid() + "/score").setValue(0);
        myRef.child("user/" + user.getUid() + "/currentSlime").setValue("blue");
        myRef.child("user/" + user.getUid() + "/currentEffect").setValue("stars");
        myRef.child("user/" + user.getUid() + "/purchasedOutfits").setValue(new ArrayList<>(Arrays.asList("blue")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
    }
}
