package com.tmd.tmd.TheMagicSlime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class StartGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(new GamePanel(StartGameActivity.this));
    }

    public void endActivity() {
        if (!isFinishing()) {
            finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void showAd(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
    }


}

