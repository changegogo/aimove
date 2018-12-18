package com.aimove.www;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.aimove.view.GameView;

public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.gameView);
        /*gameView = new GameView(getApplicationContext());
        setContentView(gameView);*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameView.handTouch(event);
        return super.onTouchEvent(event);
    }
}
