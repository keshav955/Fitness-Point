package com.example.hunny.fitnesspoint;

import android.animation.StateListAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hunny.fitnesspoint.R;

public class Activity extends AppCompatActivity {

    CardView occationally;
    CardView active;
    CardView beast_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        occationally = findViewById(R.id.occationally);
        active       = findViewById(R.id.active);
        beast_mode   = findViewById(R.id.beast_mode);

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            }
        });

        occationally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            }
        });

        beast_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            }
        });
    }

    public void Back(View view)
    {
        finish();
    }


    public void Next(View view)
    {
        Intent i = new Intent(Activity.this,Main_layout.class);
        startActivity(i);
    }
}
