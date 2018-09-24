package com.example.jose_.kitfix;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends Activity {
    private final int DURACION_SPLASH = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
        /*AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //un hilo para que tenga tiempo de ejecucion
        Thread miTimer = new Thread(){
            public void run(){
                try
                {
                    sleep(7000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                    Intent openLoginActivity= new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(openLoginActivity);
                }
            }
        };

        miTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }*/
}
