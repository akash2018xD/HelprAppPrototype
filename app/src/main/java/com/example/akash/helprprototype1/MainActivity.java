package com.example.akash.helprprototype1;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView paw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paw=(ImageView)findViewById(R.id.paw);
        paw.setBackgroundResource(R.drawable.paw);
        Thread obj = new Thread() {
            @Override
            public void run() {
                try {
                    AnimationDrawable anim=(AnimationDrawable)paw.getBackground();
                    anim.start();
                    sleep(3000); //setting splash screen time in millisecs
                    Intent obj2 = new Intent(getApplicationContext(),HelprHome.class);
                    startActivity(obj2);
                    finish();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        obj.start();
    }
}
