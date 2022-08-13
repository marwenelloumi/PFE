package com.example.kheerMaro.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.kheerMaro.MainActivity;
import com.example.kheerMaro.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_TIME8OUT= 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(SPLASH_TIME8OUT);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
