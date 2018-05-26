package com.example.hunny.fitnesspoint;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    ImageView bicep;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bicep = findViewById(R.id.fit);

        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation);

        bicep.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(this);

    }

        @Override
        public void onAnimationStart (Animation animation)
        {

        }

        @Override
        public void onAnimationEnd (Animation animation)
        {
            Handler h = new Handler();

            Runnable r = new Runnable() {
            @Override
            public void run() {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                if(auth.getCurrentUser() != null)
                {
                    Intent i = new Intent(MainActivity.this,Main_layout.class);

                    startActivity(i);

                    finish();
                }

                else {
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);

                    startActivity(i);

                    finish();
                }
            }
        };

        h.postDelayed(r, 800);

        }

        @Override
        public void onAnimationRepeat (Animation animation)
        {

        }

        /*Handler h = new Handler();

        Runnable r = new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(RecycleView_new.this, Login1.class);

                startActivity(i);
            }
        };

        h.postDelayed(r, 3000);
*/
    }
