package com.deshbangla.shrimpandfish.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.deshbangla.shrimpandfish.R;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar pb;
    private int prgrs;

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }
        },1500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pb = findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (prgrs = 10; prgrs<=100; prgrs = prgrs+10){
                    try {
                        Thread.sleep(100);
                        pb.setProgress(prgrs);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }
}