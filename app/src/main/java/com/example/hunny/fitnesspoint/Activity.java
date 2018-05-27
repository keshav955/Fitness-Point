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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.R;
import com.example.hunny.fitnesspoint.dataModel.SignUpData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Activity extends AppCompatActivity {

    CardView occationally;
    CardView active;
    CardView beast_mode;

    View occational_view, active_view,beast_view;

    public static  String activity ="";

    TextView occationally_txt , active_txt , beast_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        occationally = findViewById(R.id.occationally);
        active       = findViewById(R.id.active);
        beast_mode   = findViewById(R.id.beast_mode);
        occationally_txt = findViewById(R.id.occationally_txt);
        active_txt = findViewById(R.id.active_txt);
        beast_txt = findViewById(R.id.beast_txt);
        occational_view = findViewById(R.id.occationally_view);
        active_view = findViewById(R.id.active_view);
        beast_view = findViewById(R.id.beast_mode_view);


        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                activity = "Active";

                    Toast.makeText(Activity.this,activity,Toast.LENGTH_SHORT).show();
                    occationally.setCardBackgroundColor(Color.WHITE);
                    active.setCardBackgroundColor(Color.DKGRAY);
                    beast_mode.setCardBackgroundColor(Color.WHITE);

                    occationally_txt.setTextColor(Color.argb(255,119,119,119));
                    beast_txt.setTextColor(Color.argb(255,119,119,119));
                    active_txt.setTextColor(Color.WHITE);

                    active_view.setVisibility(View.INVISIBLE);
                    occational_view.setVisibility(View.VISIBLE);
                    beast_view.setVisibility(View.VISIBLE);

            }
        });

        occationally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                activity = "Occationally";

                Toast.makeText(Activity.this,activity,Toast.LENGTH_SHORT).show();

                    occationally.setCardBackgroundColor(Color.DKGRAY);
                    active.setCardBackgroundColor(Color.WHITE);
                    beast_mode.setCardBackgroundColor(Color.WHITE);

                occationally_txt.setTextColor(Color.WHITE);
                beast_txt.setTextColor(Color.argb(255,119,119,119));
                active_txt.setTextColor(Color.argb(255,119,119,119));
                active_view.setVisibility(View.VISIBLE);
                occational_view.setVisibility(View.INVISIBLE);
                beast_view.setVisibility(View.VISIBLE);

            }
        });

        beast_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                activity = "Beast Mode";

                Toast.makeText(Activity.this,activity,Toast.LENGTH_SHORT).show();

                occationally.setCardBackgroundColor(Color.WHITE);
                active.setCardBackgroundColor(Color.WHITE);
                beast_mode.setCardBackgroundColor(Color.DKGRAY);

                beast_txt.setTextColor(Color.WHITE);
                occationally_txt.setTextColor(Color.argb(255,119,119,119));
                active_txt.setTextColor(Color.argb(255,119,119,119));


                active_view.setVisibility(View.VISIBLE);
                occational_view.setVisibility(View.VISIBLE);
                beast_view.setVisibility(View.INVISIBLE);


            }
        });
    }

    public void Back(View view)
    {
        finish();
    }


    public void Next(View view)
    {
        if (activity.equals(""))
        {
            Toast.makeText(Activity.this,"Please Choose Your Activity Type",Toast.LENGTH_SHORT).show();

            return;
        }

        SignUpData activity_type = new SignUpData(activity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth authenticate = FirebaseAuth.getInstance();

        database.getReference().child("Goals").child(authenticate.getCurrentUser().getEmail().replace(".","")).setValue(activity_type);

        Intent i = new Intent(Activity.this,Main_layout.class);
        i.putExtra("activity", activity);
        startActivity(i);
        finish();
    }
}
