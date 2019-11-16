package com.tmd.tmd.TheMagicSlime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference();
    boolean isSignedOut = false;

   public void playView(final View v){
        finish();
        Intent i = new Intent(Main3Activity.this,Main5Activity.class);
        startActivity(i);
    }

    public void playView2(final View v){
        finish();
        Intent i = new Intent(Main3Activity.this,MainActivity.class);
        startActivity(i);
    }


    public void Signin(final View v){
        final EditText email = findViewById(R.id.edtEmail);
        final EditText pwd = findViewById(R.id.edtPassword);

        try{
            mAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() && isSignedOut) {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("username", email.getText().toString());
                                editor.putString("password", pwd.getText().toString());
                                editor.commit();
                                isSignedOut = false;
                                playView2(v);
                            } else {
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_LONG;
                                CharSequence text = "Incorrect login";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                                toast.show();
                            }
                            if(!isSignedOut){
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_LONG;
                                CharSequence text = "Sign out first";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                                toast.show();
                            }

                            // ...
                        }
                    });
        }catch (Exception e) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            CharSequence text = "Incorrect login, please try again";
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP|Gravity.START, 0, 0);
            toast.show();
        }
    }

    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        isSignedOut = true;

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        CharSequence text = "Successful signed out";
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.START, 0, 0);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
    }
}
