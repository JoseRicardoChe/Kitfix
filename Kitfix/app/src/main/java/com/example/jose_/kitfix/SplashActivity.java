package com.example.jose_.kitfix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

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
    }
}
