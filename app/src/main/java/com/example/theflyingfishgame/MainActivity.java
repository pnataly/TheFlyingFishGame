package com.example.theflyingfishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView flyingFishView;
    private Handler handler = new Handler();
    private static final long INTERVAL = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flyingFishView = new FlyingFishView(this);
        setContentView(flyingFishView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        flyingFishView.invalidate();
                    }
                });
            }
        }, 0, INTERVAL);
    }
}
